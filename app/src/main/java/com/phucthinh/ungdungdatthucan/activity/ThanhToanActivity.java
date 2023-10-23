package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.GioHang;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar mtbGioHangThanhToan;
    TextView mtvTongTienThanhToan, mtvSoDienThoai, mtvEmail;
    EditText medtDiaChi;
    AppCompatButton mbtnDatHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    long total;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        initView();
        initControl();
        countItem();
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i <Utils.mangGioHangThanhToan.size(); i++)
        {
            totalItem = totalItem +Utils.mangGioHangThanhToan.get(i).getSoLuong();
        }
    }

    private void initControl() {
        setSupportActionBar(mtbGioHangThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbGioHangThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        total = getIntent().getLongExtra("tongTien", 0);
        mtvTongTienThanhToan.setText(decimalFormat.format(total));
        mtvEmail.setText(Utils.khachHang_current.getEmail());
        mtvSoDienThoai.setText(Utils.khachHang_current.getSoDienThoai());

        mbtnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getDiaChi = medtDiaChi.getText().toString().trim();
                if (TextUtils.isEmpty(getDiaChi))
                {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //check if user is logged in before proceeding with the order
                    if(Utils.khachHang_current.getEmail() == null && Utils.khachHang_current.getMatKhau() == null ){
                        Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //post data
                    String strEmail = Utils.khachHang_current.getEmail();
                    int userId = Utils.khachHang_current.getMaKH();
                    String currentTime = getCurrentTimeFormatted();
                    Log.d("Đơn hàng", new Gson().toJson(Utils.mangGioHangThanhToan));
                    compositeDisposable.add(apiMonAn.taoDonHang(strEmail,String.valueOf(total),userId,getDiaChi, totalItem, new Gson().toJson(Utils.mangGioHangThanhToan), currentTime)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    khachHangModel -> {
                                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        for (int i = 0; i<Utils.mangGioHangThanhToan.size(); i++)
                                        {
                                            GioHang gioHang = Utils.mangGioHangThanhToan.get(i);
                                            if (Utils.mangGioHang.contains(gioHang))
                                            {
                                                Utils.mangGioHang.remove(gioHang);
                                            }
                                        }
                                        Utils.mangGioHangThanhToan.clear();
                                        Paper.book().write("giohang", Utils.mangGioHang);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
    private String getCurrentTimeFormatted() {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();
        // Create a Date object using the current time
        Date currentDate = new Date(currentTimeMillis);
        // Format the Date object into the desired string representation
        return sdf.format(currentDate);
    }

    private void initView() {
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        mtbGioHangThanhToan = findViewById(R.id.tbGioHangThanhToan);
        mtvTongTienThanhToan = findViewById(R.id.tvTongTienThanhToan);
        mtvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        mtvEmail = findViewById(R.id.tvEmail);
        mbtnDatHang = findViewById(R.id.btnDatHang);
        medtDiaChi = findViewById(R.id.edtDiaChi);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}