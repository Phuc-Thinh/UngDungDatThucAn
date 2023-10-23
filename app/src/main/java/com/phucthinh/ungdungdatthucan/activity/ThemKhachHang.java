package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.KhachHang;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemKhachHang extends AppCompatActivity {

    Spinner mspnQuyen;
    Toolbar mtbThemKH;
    int MaQuyen = 0;
    Button mbtnThemKhachHang;
    EditText mHovaTen, mSoDienThoai, mEmail, mDiaChi, mMatKhau;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    KhachHang suaKhachHang;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        initView();
        initData();
    }

    private void initData() {
        List<String> strList = new ArrayList<>();
        strList.add("Chọn quyền: ");
        strList.add("Quản trị viên");
        strList.add("Khách hàng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strList);
        mspnQuyen.setAdapter(adapter);
        mspnQuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MaQuyen = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mbtnThemKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themKhachHang();

            }
        });
    }
    private void themKhachHang(){
        String getHovaTen = mHovaTen.getText().toString().trim();
        String getSoDienThoai = mSoDienThoai.getText().toString().trim();
        String getEmail = mEmail.getText().toString().trim();
        String getDiaChi = mDiaChi.getText().toString().trim();
        String getMatKhau = mMatKhau.getText().toString().trim();
        String emailMau = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getHovaTen) || TextUtils.isEmpty(getSoDienThoai) || TextUtils.isEmpty(getEmail)
                || TextUtils.isEmpty(getDiaChi)|| TextUtils.isEmpty(getMatKhau) || MaQuyen == 0){
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getHovaTen)){
            Toast.makeText(getApplicationContext(),"Tên đầy đủ không bao gồm số", Toast.LENGTH_SHORT).show();
        }else if (getMatKhau.length() < 8){
            Toast.makeText(getApplicationContext(),"Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
        }else if (getSoDienThoai.length() !=10){
            Toast.makeText(getApplicationContext(),"Số điện thoại phải dài ít nhất 10 ký tự", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailMau)){
            Toast.makeText(getApplicationContext(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiMonAn.themKhachHang(getHovaTen, getSoDienThoai,getEmail,getDiaChi,getMatKhau,(MaQuyen))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            thongBaoModel -> {
                                if(thongBaoModel.isThanhcong()) {
                                    Toast.makeText(getApplicationContext(), thongBaoModel.getThongbao(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),QuanLyKhachHang.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), thongBaoModel.getThongbao(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Log.d("log",throwable.getMessage());
                            }
                    ));
        }
    }

    private void initView() {
        mspnQuyen = findViewById(R.id.spnQuyen);
        mtbThemKH = findViewById(R.id.tbThemKH);
        mbtnThemKhachHang = findViewById(R.id.btnThemKhachHang);
        mHovaTen = findViewById(R.id.edtHovaTen);
        mSoDienThoai = findViewById(R.id.edtSoDienThoai1);
        mEmail = findViewById(R.id.edtemail);
        mDiaChi = findViewById(R.id.edtDiaChi2);
        mMatKhau = findViewById(R.id.edtMatKhau2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}