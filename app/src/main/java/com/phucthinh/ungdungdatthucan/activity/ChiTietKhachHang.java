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

public class ChiTietKhachHang extends AppCompatActivity {

    Spinner mspinQuyen;
    int MaQuyen = 0;
    KhachHang chitietKhachHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    Button mbtnSuaKH;
    Toolbar mtbSua;
    EditText medtTenKH, medtSoDienThoai, medtEmail, medtDiaChi, medtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_hang);

        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);

        initView();
        initData();
        initControl();
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(mtbSua);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbSua.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mspinQuyen = findViewById(R.id.spinQuyen);
        medtTenKH = findViewById(R.id.edtTenKH);
        medtSoDienThoai = findViewById(R.id.edtSDT);
        medtEmail = findViewById(R.id.edtEmail1);
        medtDiaChi = findViewById(R.id.edtDiaChi1);
        mbtnSuaKH = findViewById(R.id.btnSuaKH);
        medtMatKhau = findViewById(R.id.edtMatKhau1);
        mtbSua = findViewById(R.id.tbSua);
    }

    private void initData() {
        chitietKhachHang = (KhachHang) getIntent().getSerializableExtra("chitietkhachhang");
        List<String> strList = new ArrayList<>();
        strList.add("Quản trị viên");
        strList.add("Khách hàng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strList);
        mspinQuyen.setAdapter(adapter);
        mspinQuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MaQuyen = i + 1;
//                String s = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(DetailUserActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ChiTietKhachHang.this, "Không có gì được chọn", Toast.LENGTH_SHORT).show();
            }
        });
        medtTenKH.setText(chitietKhachHang.getTenKH());
        medtSoDienThoai.setText(chitietKhachHang.getSoDienThoai());
        medtEmail.setText(chitietKhachHang.getEmail());
        medtDiaChi.setText(chitietKhachHang.getDiaChi());
        medtMatKhau.setText(chitietKhachHang.getMatKhau());
    }
    private void initControl() {
        mbtnSuaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaKhachHang(chitietKhachHang);
                Intent intent = new Intent(getApplicationContext(),QuanLyKhachHang.class);
                startActivity(intent);
            }
        });
    }

    private void suaKhachHang(KhachHang khachHang){
        String getTenKH = medtTenKH.getText().toString().trim();
        String getSoDienThoai = medtSoDienThoai.getText().toString().trim();
        String getEmail = medtEmail.getText().toString().trim();
        String getDiaChi = medtDiaChi.getText().toString().trim();
        String getMatKhau = medtMatKhau.getText().toString().trim();
        String emailMau = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getTenKH) || TextUtils.isEmpty(getSoDienThoai)
                || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getDiaChi) || TextUtils.isEmpty(getMatKhau) || MaQuyen == 0 ){
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getTenKH)){
            Toast.makeText(getApplicationContext(),"Tên đầy đủ không bao gồm số", Toast.LENGTH_SHORT).show();
        }else if (getSoDienThoai.length() != 10 ) {
            Toast.makeText(getApplicationContext(), "Số điện thoại phải dài ít nhất 10 ký tự", Toast.LENGTH_SHORT).show();
        }else if (getMatKhau.length() < 8){
            Toast.makeText(getApplicationContext(),"Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailMau)){
            Toast.makeText(getApplicationContext(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiMonAn.suaKhachHang(getTenKH, getSoDienThoai, getEmail, getDiaChi, getMatKhau, (MaQuyen), khachHang.getMaKH())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            thongBaoModel ->  {
                                if(thongBaoModel.isThanhcong()) {
                                    Toast.makeText(getApplicationContext(), thongBaoModel.getThongbao(), Toast.LENGTH_SHORT).show();
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

}