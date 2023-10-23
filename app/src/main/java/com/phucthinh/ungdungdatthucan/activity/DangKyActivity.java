package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    TextView mtvDangNhap;
    EditText medtHoTen, medtSoDienThoai, medtEmail, medtDiaChi, medtMatKhau;
    Button mtbnDangKy;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        initView();
        initControl();
    }

    private void initControl() {
        mtvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
        mtbnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });
    }

    private void dangKy() {
        String getHoTen = medtHoTen.getText().toString().trim();
        String getSoDienThoai = medtSoDienThoai.getText().toString().trim();
        String getEmail = medtEmail.getText().toString().trim();
        String getDiaChi = medtDiaChi.getText().toString().trim();
        String getMatKhau = medtMatKhau.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int MaQuyen = 2;
        if (TextUtils.isEmpty(getHoTen)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(getSoDienThoai)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(getEmail)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(getDiaChi)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(getMatKhau)){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getHoTen)){
            Toast.makeText(getApplicationContext(),"Tên đầy đủ không bao gồm số", Toast.LENGTH_SHORT).show();
        }else if (getMatKhau.length() < 8){
            Toast.makeText(getApplicationContext(),"Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
        }else if (getSoDienThoai.length() !=10){
            Toast.makeText(getApplicationContext(),"Số điện thoại phải dài ít nhất 10 ký tự", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailPattern)){
            Toast.makeText(getApplicationContext(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
        } else{
            compositeDisposable.add(apiMonAn.dangKy(getHoTen,getDiaChi,getSoDienThoai,getEmail,getMatKhau,MaQuyen).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            khachHangModel -> {
                                if (khachHangModel.isSuccess())
                                {
                                    Utils.khachHang_current.setEmail(getEmail);
                                    Utils.khachHang_current.setMatKhau(getMatKhau);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), khachHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }


    private void initView() {
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        mtvDangNhap = findViewById(R.id.tvDangNhap);
        medtHoTen = findViewById(R.id.edtHoTen);
        medtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        medtEmail = findViewById(R.id.edtEmail);
        medtDiaChi = findViewById(R.id.edtDiaChi);
        medtMatKhau = findViewById(R.id.edtMatKhau);
        mtbnDangKy = findViewById(R.id.btnDangKy);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}