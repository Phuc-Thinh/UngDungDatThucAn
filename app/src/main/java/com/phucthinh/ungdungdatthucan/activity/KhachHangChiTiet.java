package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.phucthinh.ungdungdatthucan.R;

public class KhachHangChiTiet extends AppCompatActivity {

    Toolbar mtbKhachHangChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang_chi_tiet);
        mtbKhachHangChiTiet = findViewById(R.id.tbKhachHangChiTiet);

        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(mtbKhachHangChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbKhachHangChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}