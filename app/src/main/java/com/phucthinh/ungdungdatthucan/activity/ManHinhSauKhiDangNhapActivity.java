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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.LoaiMonAnAdapter;
import com.phucthinh.ungdungdatthucan.adapter.MonAnMoiAdapter;
import com.phucthinh.ungdungdatthucan.model.KhachHang;
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

public class ManHinhSauKhiDangNhapActivity extends AppCompatActivity {

    Toolbar mtbMain;
    ViewFlipper mvfMain;
    RecyclerView mrcvMain;
    NavigationView mngvMain;
    ListView mlvMain;
    DrawerLayout mdlMain;
    LoaiMonAnAdapter loaiMonAnAdapter;
    List<LoaiMonAn> loaiMonAnList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    TextView mtxtTenSauKhiDangNhap;
    List<MonAnMoi> monAnMoiList;
    MonAnMoiAdapter monAnMoiAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView mivDangNhap, mivTimKiem;
    KhachHang khachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_sau_khi_dang_nhap);
        Paper.init(this);
        //logout
        if(Paper.book().read("khachhang") !=null){
            KhachHang khachhang = Paper.book().read("khachhang");
            Utils.khachHang_current = khachhang;
        }

        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);

        initView();
        actionBar();
        actionViewFlipper();
        initControl();

        if (isConnected(this)) {
            actionViewFlipper();
            getLoaiMonAn();
            getMonAnMoi();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }
    private void initControl() {

    }
    private void getEventClick() {
        mlvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        Intent donHang = new Intent(getApplicationContext(),XemDonHangActivity.class);
                        startActivity(donHang);
                        break;
                }
            }
        });
        mivDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginAfter = new Intent(getApplicationContext(),ChiTietSauKhiDangNhap.class);
                startActivity(loginAfter);
            }
        });
    }

    private void getMonAnMoi() {
        compositeDisposable.add(apiMonAn.getMonAnMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        monAnMoiModel ->  {
                            if (monAnMoiModel.isSuccess())
                            {
                                monAnMoiList = monAnMoiModel.getResult();
                                monAnMoiAdapter = new MonAnMoiAdapter(getApplicationContext(), monAnMoiList);
                                mrcvMain.setAdapter(monAnMoiAdapter);
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
                            if (loaiMonAnModel.isSuccess()) {
                                loaiMonAnList = loaiMonAnModel.getResult();
                                //khoi tao adapter
                                loaiMonAnAdapter = new LoaiMonAnAdapter(getApplicationContext(), loaiMonAnList);
                                mlvMain.setAdapter(loaiMonAnAdapter);
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
            mvfMain.addView(imageView);
        }
        mvfMain.setFlipInterval(3000);
        mvfMain.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_int_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        mvfMain.setInAnimation(slide_in);
        mvfMain.setOutAnimation(slide_out);
    }

    private void actionBar() {
        setSupportActionBar(mtbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mtbMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlMain.openDrawer(GravityCompat.START);
            }
        });
        mivTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(search);
            }
        });
    }



    private void initView() {
        mtxtTenSauKhiDangNhap = findViewById(R.id.txtTenSauKhiDangNhap);
        mtxtTenSauKhiDangNhap.setText(Utils.khachHang_current.getTenKH());
        mtbMain = findViewById(R.id.tbMain);
        mvfMain = findViewById(R.id.vfMain);
        mrcvMain = findViewById(R.id.rcvMain);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this , 2);
        mrcvMain.setLayoutManager(layoutManager);
        mrcvMain.setHasFixedSize(true);
        mngvMain = findViewById(R.id.ngvMain);
        mlvMain = findViewById(R.id.lvMain);
        mdlMain = findViewById(R.id.dlMain);
        badge = findViewById(R.id.badge);
        frameLayout = findViewById(R.id.flGioHang);
        mivDangNhap = findViewById(R.id.ivDangNhap);
        mivTimKiem = findViewById(R.id.ivTimKiem);

        //khoi tao list
        loaiMonAnList = new ArrayList<>();
        monAnMoiList = new ArrayList<>();
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
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(cart);
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