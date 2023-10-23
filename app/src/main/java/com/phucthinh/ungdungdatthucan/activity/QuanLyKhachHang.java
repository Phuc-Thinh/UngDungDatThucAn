package com.phucthinh.ungdungdatthucan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.KhachHangAdapter;
import com.phucthinh.ungdungdatthucan.model.EventBus.SuaXoaKhachHangEvent;
import com.phucthinh.ungdungdatthucan.model.KhachHang;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyKhachHang extends AppCompatActivity {

    RecyclerView mrcvQLKH;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    KhachHang khachHangXoa;
    ImageView mimgThemKH;
    List<KhachHang> khachHangList;
    KhachHangAdapter khachHangAdapter;
    Toolbar mtbQuanLyKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khach_hang);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        initView();
        initControl();
        getChiTietKhachHang();
        actionBar();
        if (isConnected(this)) {
            getChiTietKhachHang();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }

    private void actionBar() {
        setSupportActionBar(mtbQuanLyKH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbQuanLyKH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mimgThemKH = findViewById(R.id.imgThemKH);
        mrcvQLKH = findViewById(R.id.rcvQLKH);
        mtbQuanLyKH = findViewById(R.id.tbQuanLyKH);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvQLKH.setHasFixedSize(true);
        mrcvQLKH.setLayoutManager(layoutManager);
    }
    private void initControl() {
        mimgThemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemKhachHang.class);
                startActivity(intent);
            }
        });
    }


    private void getChiTietKhachHang() {
        compositeDisposable.add(apiMonAn.layKhachHang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        khachHangModel ->  {
                            if (khachHangModel.isSuccess())
                            {
                                khachHangList = khachHangModel.getResult();
                                khachHangAdapter = new KhachHangAdapter(getApplicationContext(), khachHangList);
                                mrcvQLKH.setAdapter(khachHangAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    private void xoaKhachHang() {
        compositeDisposable.add(apiMonAn.xoaKH(khachHangXoa.getMaKH())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongBaoModel -> {
                            if(thongBaoModel.isThanhcong()) {
                                Toast.makeText(getApplicationContext(), thongBaoModel.getThongbao(), Toast.LENGTH_SHORT).show();
                                getChiTietKhachHang();

                            }else {
                                Toast.makeText(getApplicationContext(), thongBaoModel.getThongbao(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.d("log",throwable.getMessage());
                        }
                ));
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Xoá")){
            xoaKhachHang();
        }
        return super.onContextItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoaKhachHang(SuaXoaKhachHangEvent event){
        if(event != null)
            khachHangXoa = event.getKhachHang();
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}