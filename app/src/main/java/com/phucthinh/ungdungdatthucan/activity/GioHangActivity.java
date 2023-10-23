package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.GioHangAdapter;
import com.phucthinh.ungdungdatthucan.model.EventBus.TinhTongEvent;
import com.phucthinh.ungdungdatthucan.model.GioHang;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView mtvGioHangTrong, mtvTongTien;
    Toolbar mtbGioHang;
    RecyclerView mrcvGioHang;
    Button mbtnMua;
    GioHangAdapter gioHangAdapter;
    List<GioHang> gioHangList;
    long tongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        initView();
        initControl();
        if (Utils.mangGioHangThanhToan != null)
        {
            Utils.mangGioHangThanhToan.clear();
        }
        tinhTongTien();
    }

    private void tinhTongTien() {
        tongTien = 0;
            for (int i = 0; i < Utils.mangGioHangThanhToan.size(); i++) {
                tongTien = tongTien + (Utils.mangGioHangThanhToan.get(i).getGiaMonAn() * Utils.mangGioHangThanhToan.get(i).getSoLuong());
            }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtvTongTien.setText(decimalFormat.format(tongTien) + "đ");
    }

    private void initControl() {
        setSupportActionBar(mtbGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mrcvGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvGioHang.setLayoutManager(layoutManager);
        if (Utils.mangGioHang.size() == 0)
        {
            mtvGioHangTrong.setVisibility(View.VISIBLE);
        } else
        {
            gioHangAdapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
            mrcvGioHang.setAdapter(gioHangAdapter);
        }
        mbtnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongTien", tongTien);
                //Utils.mangGioHang.clear();
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mtvGioHangTrong = findViewById(R.id.tvGioHangTrong);
        mtbGioHang = findViewById(R.id.tbGioHang);
        mrcvGioHang = findViewById(R.id.rcvGioHang);
        mtvTongTien = findViewById(R.id.tvTongTien);
        mbtnMua = findViewById(R.id.btnMua);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void tinhTongTien(TinhTongEvent event)
    {
        if (event != null)
        {
            tinhTongTien();
        }
    }
}