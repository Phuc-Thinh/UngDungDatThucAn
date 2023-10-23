package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ChiTietSauKhiDangNhap extends AppCompatActivity {

    EditText mtxtHoTen, mtxtSoDienThoai, mtxtEmail, mtxtDiaChi;
    TextView mtxtTen;
    Button mbtnSua;
    ImageView mivMain, mivDangXuat;
    KhachHang khachHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sau_khi_dang_nhap);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        Paper.init(this);
        //dang xuat
        if(Paper.book().read("khachhang") !=null){
            KhachHang khachhang = Paper.book().read("khachhang");
            Utils.khachHang_current = khachhang;
        }
        initView();
        initControl();
    }
    private void initView() {
        mtxtHoTen = findViewById(R.id.txtHoTen);
        mtxtHoTen.setText(Utils.khachHang_current.getTenKH());
        mtxtTen = findViewById(R.id.txtTen);
        mtxtTen.setText(Utils.khachHang_current.getTenKH());
        mtxtSoDienThoai = findViewById(R.id.txtSoDienThoai);
        mtxtSoDienThoai.setText(Utils.khachHang_current.getSoDienThoai());
        mtxtEmail = findViewById(R.id.txtEmail);
        mtxtEmail.setText(Utils.khachHang_current.getEmail());
        mtxtDiaChi = findViewById(R.id.txtDiaChi);
        mtxtDiaChi.setText(Utils.khachHang_current.getDiaChi());
        mbtnSua = findViewById(R.id.btnSua);
        mivMain = findViewById(R.id.ivMain);
        mivDangXuat = findViewById(R.id.ivDangXuat1);
    }
    private void initControl() {
        mbtnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaKhachHang(khachHang);
            }


        });
        mivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent afterLog = new Intent(getApplicationContext(), ManHinhSauKhiDangNhapActivity.class);
                startActivity(afterLog);
                finish();
            }
        });
        mivDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //del key user
                Paper.book().delete("khachhang");
                Utils.khachHang_current = new KhachHang();
                Intent dangXuat = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(dangXuat);
                finish();

                Toast.makeText(getApplicationContext(),"Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void suaKhachHang(KhachHang khachHang) {
        String getHoTen = mtxtHoTen.getText().toString().trim();
        String getSoDienThoai = mtxtSoDienThoai.getText().toString().trim();
        String getEmail = mtxtEmail.getText().toString().trim();
        String getDiaChi = mtxtDiaChi.getText().toString().trim();
        String emailMau = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getHoTen) || TextUtils.isEmpty(getSoDienThoai)
                || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getDiaChi)){
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getHoTen)){
            Toast.makeText(getApplicationContext(),"Tên đầy đủ không bao gồm số", Toast.LENGTH_SHORT).show();
        }else if (getSoDienThoai.length() != 10 ) {
            Toast.makeText(getApplicationContext(), "Số điện thoại phải dài ít nhất 10 ký tự", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailMau)){
            Toast.makeText(getApplicationContext(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiMonAn.suaNguoiDungKH(getHoTen, getSoDienThoai, getEmail, getDiaChi, khachHang.getMaKH())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            thongBaoModel -> {
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