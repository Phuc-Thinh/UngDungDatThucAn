package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.KhachHang;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView mtxtDangKi;
    EditText mTaiKhoan, mMatKhau;
    Button mbtnDangNhap;
    ApiMonAn apiMonAn;

    Toolbar mtbDangNhap;
    int MaQuyen;
    boolean isDangNhap = false;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
        actionBar();
        MaQuyen = getIntent().getIntExtra("MaQuyen", 2);

    }

    private void actionBar() {
        setSupportActionBar(mtbDangNhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbDangNhap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initControl() {
        mtxtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        mbtnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void initView() {
        Paper.init(this);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        mtxtDangKi = findViewById(R.id.txtDangKi);
        mTaiKhoan = findViewById(R.id.taikhoan);
        mMatKhau = findViewById(R.id.matkhau);
        mbtnDangNhap = findViewById(R.id.btnDangNhap);
        mtbDangNhap = findViewById(R.id.tbDangNhap);

        //read to save for the next login
        if (Paper.book().read("Email") != null && Paper.book().read("Mật khẩu") != null){
        }
    }

    private void isDangNhap(String email, String matkhau) {
        compositeDisposable.add(apiMonAn.dangNhap(email, matkhau)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        khachHangModel ->  {
                            if(khachHangModel.isSuccess()) {
                                isDangNhap = true;
                                Paper.book().write("isDangNhap", isDangNhap);
                                Utils.khachHang_current = khachHangModel.getResult().get(0);
                                // save data user
                                Paper.book().write("khachhang", khachHangModel.getResult().get(0));
                                KhachHang khachHang = khachHangModel.getResult().get(0);

                                Intent intent = new Intent(getApplicationContext(), khachHang.getMaQuyen() == 2 ? ManHinhSauKhiDangNhapActivity.class : QuanTriVienActivity.class);

                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void Login(){
        String get_taikhoan = mTaiKhoan.getText().toString().trim();
        String get_matkhau = mMatKhau.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(get_taikhoan)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_matkhau)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }else if (!get_taikhoan.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }else if (get_matkhau.length() < 8){
            Toast.makeText(getApplicationContext(),"Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
        } else{
            //save data
            Paper.book().write("Email", get_taikhoan);
            Paper.book().write("MatKhau", get_matkhau);
            isDangNhap(get_taikhoan,get_matkhau);
//
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.khachHang_current != null && Utils.khachHang_current.getEmail() != null && Utils.khachHang_current.getMatKhau() != null) {
            mTaiKhoan.setText(Utils.khachHang_current.getEmail());
            mMatKhau.setText(Utils.khachHang_current.getMatKhau());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }

}