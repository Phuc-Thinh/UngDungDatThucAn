package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.GioHang;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.text.DecimalFormat;

import io.paperdb.Paper;

public class ChiTietActivity extends AppCompatActivity {
    TextView mtvTenMonAn, mtvGiaMonAn, mtvMoTaMonAn;
    Button mbtnThemVaoGioHang;
    ImageView mimgChiTiet;
    Spinner mspnSoLuong;
    Toolbar mtbChiTiet;

    MonAnMoi monAnMoi;
    NotificationBadge notificationBadge;

    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        
        initView();
        actionBar();
        initData();
        initControl();
    }

    private void initControl() {
        mbtnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themVaoGioHang();
                Paper.book().write("giohang", Utils.mangGioHang);
            }
        });
    }

    private void themVaoGioHang() {
        if(Utils.mangGioHang.size() > 0)
        {
            boolean flag = false;
            int size = Integer.parseInt(mspnSoLuong.getSelectedItem().toString());
            for(int i = 0; i < Utils.mangGioHang.size(); i++)
            {
                if (Utils.mangGioHang.get(i).getMaMonAn() == monAnMoi.getMaMonAn())
                {
                    Utils.mangGioHang.get(i).setSoLuong(size + Utils.mangGioHang.get(i).getSoLuong());
                    flag = true;
                }
            }
            if (flag == false)
            {
                long gia = Long.parseLong(monAnMoi.getGiaMonAn().trim());
                GioHang gioHang = new GioHang();
                gioHang.setGiaMonAn(gia);
                gioHang.setSoLuong(size);
                gioHang.setMaMonAn(monAnMoi.getMaMonAn());
                gioHang.setTenMonAn(monAnMoi.getTenMonAn());
                gioHang.setHinhAnhMonAn(monAnMoi.getHinhAnhMonAn());
                Utils.mangGioHang.add(gioHang);
            }
        }
        else
        {
            int size = Integer.parseInt(mspnSoLuong.getSelectedItem().toString());
            long gia = Long.parseLong(monAnMoi.getGiaMonAn().trim());
            GioHang gioHang = new GioHang();
            gioHang.setGiaMonAn(gia);
            gioHang.setSoLuong(size);
            gioHang.setMaMonAn(monAnMoi.getMaMonAn());
            gioHang.setTenMonAn(monAnMoi.getTenMonAn());
            gioHang.setHinhAnhMonAn(monAnMoi.getHinhAnhMonAn());
            Utils.mangGioHang.add(gioHang);
        }
        int totalItem = 0;
        for (int i = 0; i <Utils.mangGioHang.size(); i++)
        {
            totalItem = totalItem +Utils.mangGioHang.get(i).getSoLuong();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        monAnMoi = (MonAnMoi) getIntent().getSerializableExtra("chitiet");
        mtvTenMonAn.setText(monAnMoi.getTenMonAn());
        mtvMoTaMonAn.setText(monAnMoi.getMoTaMonAn());
        Glide.with(getApplicationContext()).load(monAnMoi.getHinhAnhMonAn()).into(mimgChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtvGiaMonAn.setText("Giá: " + decimalFormat.format(Double.parseDouble(monAnMoi.getGiaMonAn().trim())) + "đ");
        Integer[] size = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, size);
        mspnSoLuong.setAdapter(arrayAdapter);
    }

    private void actionBar() {
        setSupportActionBar(mtbChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mtvTenMonAn = findViewById(R.id.tvTenMonAn);
        mtvGiaMonAn = findViewById(R.id.tvGiaMonAn);
        mtvMoTaMonAn = findViewById(R.id.tvMoTaMonAn);
        mbtnThemVaoGioHang = findViewById(R.id.btnThemVaoGioHang);
        mimgChiTiet = findViewById(R.id.imgChiTiet);
        mspnSoLuong = findViewById(R.id.spnSoLuong);
        mtbChiTiet = findViewById(R.id.tbChiTiet);
        notificationBadge = findViewById(R.id.badge);
        frameLayout = findViewById(R.id.flGioHang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
        if (Utils.mangGioHang != null)
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.mangGioHang.size(); i++)
            {
                totalItem = totalItem +Utils.mangGioHang.get(i).getSoLuong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.mangGioHang != null)
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.mangGioHang.size(); i++)
            {
                totalItem = totalItem +Utils.mangGioHang.get(i).getSoLuong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
}