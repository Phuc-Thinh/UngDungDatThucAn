package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.LoaiMonAnAdapter;
import com.phucthinh.ungdungdatthucan.adapter.MonAnMoiAdapter;
import com.phucthinh.ungdungdatthucan.model.LoaiMonAn;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar mtbTrangChinh;
    ViewFlipper mvfTrangChinh;
    RecyclerView mrcvTrangChinh;
    NavigationView mngvTrangChinh;
    ListView mlvTrangChinh;
    DrawerLayout mdlTrangChinh;
    LoaiMonAnAdapter loaiMonAnAdapter;
    List<LoaiMonAn> loaiMonAnList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    List<MonAnMoi> monAnMoiModelList;
    MonAnMoiAdapter monAnMoiAdapter;
    NotificationBadge badge;
    FrameLayout flGioHang;
    ImageView mivDangNhap, mivTimKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);

        initView();
        actionBar();
        if (isConnected(this)) {
            actionViewFlipper();
            getLoaiMonAn();
            getMonAnMoi();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEventClick() {
        mlvTrangChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        Intent uuDai = new Intent(getApplicationContext(), UuDaiActivity.class);
                        uuDai.putExtra("MaLoaiMonAn", 1);
                        startActivity(uuDai);
                        break;
                    case 1:
                        Intent monMoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        monMoi.putExtra("MaLoaiMonAn", 2);
                        startActivity(monMoi);
                        break;
                    case 2:
                        Intent combo1Nguoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        combo1Nguoi.putExtra("MaLoaiMonAn", 3);
                        startActivity(combo1Nguoi);
                        break;
                    case 3:
                        Intent comboNhom = new Intent(getApplicationContext(), UuDaiActivity.class);
                        comboNhom.putExtra("MaLoaiMonAn", 4);
                        startActivity(comboNhom);
                        break;
                    case 4:
                        Intent burgerComMiY = new Intent(getApplicationContext(), UuDaiActivity.class);
                        burgerComMiY.putExtra("MaLoaiMonAn", 5);
                        startActivity(burgerComMiY);
                        break;
                    case 5:
                        Intent thucUongTrangMieng = new Intent(getApplicationContext(), UuDaiActivity.class);
                        thucUongTrangMieng.putExtra("MaLoaiMonAn", 6);
                        startActivity(thucUongTrangMieng);
                        break;
                    case 6:
                        Intent donHang = new Intent(getApplicationContext(), XemDonHangActivity.class);
                        startActivity(donHang);
                        break;
                }
            }
        });
        mivDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getMonAnMoi() {
        compositeDisposable.add(apiMonAn.getMonAnMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        monAnMoiModel -> {
                            if (monAnMoiModel.isSuccess())
                            {
                                monAnMoiModelList = monAnMoiModel.getResult();
                                monAnMoiAdapter = new MonAnMoiAdapter(getApplicationContext(), monAnMoiModelList);
                                mrcvTrangChinh.setAdapter(monAnMoiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void getLoaiMonAn() {
        compositeDisposable.add(apiMonAn.getLoaiMonAn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiMonAnModel -> {
                            if (loaiMonAnModel.isSuccess())
                            {
                                loaiMonAnList = loaiMonAnModel.getResult();
                                loaiMonAnAdapter = new LoaiMonAnAdapter(getApplicationContext(), loaiMonAnList);
                                mlvTrangChinh.setAdapter(loaiMonAnAdapter);
                            }
                        }
                ));
    }

    private void actionViewFlipper() {
        int[] arrAd = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        for (int i = 0; i < arrAd.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(arrAd[i]);
            mvfTrangChinh.addView(imageView);
        }
        mvfTrangChinh.setFlipInterval(3000);
        mvfTrangChinh.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_int_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        mvfTrangChinh.setInAnimation(slide_in);
        mvfTrangChinh.setOutAnimation(slide_out);
    }

    private void actionBar() {
        setSupportActionBar(mtbTrangChinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbTrangChinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mtbTrangChinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlTrangChinh.openDrawer(GravityCompat.START);
            }
        });

    }

    private void initView() {
        mivTimKiem = findViewById(R.id.ivTimKiem);
        mtbTrangChinh = findViewById(R.id.tbTrangChinh);
        mvfTrangChinh = findViewById(R.id.vfTrangChinh);
        mrcvTrangChinh = findViewById(R.id.rcvTrangChinh);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mrcvTrangChinh.setLayoutManager(layoutManager);
        mrcvTrangChinh.setHasFixedSize(true);
        mngvTrangChinh = findViewById(R.id.ngvTrangChinh);
        mlvTrangChinh = findViewById(R.id.lvTrangChinh);
        mdlTrangChinh = findViewById(R.id.dlTrangChinh);
        badge = findViewById(R.id.badge);
        flGioHang = findViewById(R.id.flGioHang);
        mivDangNhap = findViewById(R.id.ivDangNhap);
        //khoi tao list
        loaiMonAnList = new ArrayList<>();
        monAnMoiModelList = new ArrayList<>();
        if (Paper. book().read("giohang") != null)
        {
            Utils.mangGioHang = Paper.book().read("giohang");
        }
        if(Utils.mangGioHang == null)
        {
            Utils.mangGioHang = new ArrayList<>();
        }
        else
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.mangGioHang.size(); i++)
            {
                totalItem = totalItem +Utils.mangGioHang.get(i).getSoLuong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        flGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
        mivTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i <Utils.mangGioHang.size(); i++)
        {
            totalItem = totalItem +Utils.mangGioHang.get(i).getSoLuong();
        }
        badge.setText(String.valueOf(totalItem));
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
}