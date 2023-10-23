package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.KhachHang;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class QuanTriVienActivity extends AppCompatActivity {

    Button mbtnThongKe, mbtnThemKH, mbtnThemMonAn,mbtnThemDH;
    ImageView mivDangXuat, mivTrang;
    TextView mtxtTenQTV;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText mEmail, mMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_tri_vien);
        Paper.init(this);
        //logout
        if(Paper.book().read("khachhang") !=null){
            KhachHang khachHang = Paper.book().read("khachhang");
            Utils.khachHang_current = khachHang;
        }
        initView();
        initControl();
    }

    private void initView() {
        mbtnThongKe= findViewById(R.id.btnThongKe);
        mbtnThemKH= findViewById(R.id.btnThemKH);
        mbtnThemMonAn = findViewById(R.id.btnThemMonAn);
        mbtnThemDH = findViewById(R.id.btnThemDH);
        mivTrang = findViewById(R.id.ivTrangChinh);
        mivDangXuat = findViewById(R.id.ivDangXuat);
        mtxtTenQTV = findViewById(R.id.txtTenQTV);
        mtxtTenQTV.setText(Utils.khachHang_current.getTenKH());
        mEmail = findViewById(R.id.edtEmail);
        mMatKhau = findViewById(R.id.edtMatKhau);
    }

    private void initControl() {
        mbtnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tk = new Intent(getApplicationContext(), ThongKeActivity.class);
                startActivity(tk);
            }
        });
        mbtnThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent khachHang = new Intent(getApplicationContext(), QuanLyKhachHang.class);
                startActivity(khachHang);
            }
        });
        mbtnThemMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monAn = new Intent(getApplicationContext(), QuanliSP.class);
                startActivity(monAn);
            }
        });
        mbtnThemDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order = new Intent(getApplicationContext(), XemDonHangActivity.class);
                startActivity(order);
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
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}