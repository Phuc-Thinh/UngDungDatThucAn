package com.phucthinh.ungdungdatthucan.activity;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.LoaiMonAnAdapter;
import com.phucthinh.ungdungdatthucan.adapter.MonAnMoiAdapter;
import com.phucthinh.ungdungdatthucan.model.EventBus.SuaXoaEvent;
import com.phucthinh.ungdungdatthucan.model.LoaiMonAn;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanliSP extends AppCompatActivity {

    ImageButton mbtnThem;
    RecyclerView mrvQuanLi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    NavigationView mngvTrang;
    ListView mlvTrang;
    DrawerLayout mdlTrang;
    Toolbar mtbQuanLi;
    LoaiMonAnAdapter loaiMonAnAdapter;
    List<LoaiMonAn> loaiMonAnList;
    List<MonAnMoi> monAnMoiList;
    MonAnMoiAdapter monAnMoiAdapter;
    MonAnMoi sanPhamSuaXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_sp);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        initView();
        actionBar();
        initControl();
        getMonAn();

        if (isConnected(this)) {
            getLoaiMonAn();
            getMonAn();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }
    private void initControl(){
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSP.class);
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(mtbQuanLi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbQuanLi.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mtbQuanLi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlTrang.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getEventClick() {
        mlvTrang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent uuDai = new Intent(getApplicationContext(), UuDaiActivity.class);
                        uuDai.putExtra("MaLoaiMonAn", 2);
                        startActivity(uuDai);
                        break;
                    case 1:
                        Intent monMoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        monMoi.putExtra("MaLoaiMonAn", 3);
                        startActivity(monMoi);
                        break;
                    case 2:
                        Intent combo1Nguoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        combo1Nguoi.putExtra("MaLoaiMonAn", 4);
                        startActivity(combo1Nguoi);
                        break;
                    case 3:
                        Intent comboNhom = new Intent(getApplicationContext(), UuDaiActivity.class);
                        comboNhom.putExtra("MaLoaiMonAn", 5);
                        startActivity(comboNhom);
                        break;
                    case 4:
                        Intent burgerComMiY = new Intent(getApplicationContext(), UuDaiActivity.class);
                        burgerComMiY.putExtra("MaLoaiMonAn", 6);
                        startActivity(burgerComMiY);
                        break;
                    case 5:
                        Intent thucUongTrangMieng = new Intent(getApplicationContext(), UuDaiActivity.class);
                        thucUongTrangMieng.putExtra("MaLoaiMonAn", 7);
                        startActivity(thucUongTrangMieng);
                        break;
                    case 6:
                        Intent intent = new Intent(getApplicationContext(), XemDonHangActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        Intent tk= new Intent(getApplicationContext(), ThongKeActivity.class);
                        startActivity(tk);
                        break;
                    case 8:
                        Intent exit= new Intent(getApplicationContext(), QuanTriVienActivity.class);
                        startActivity(exit);
                        break;
                }
            }
        });
    }
    private void getMonAn() {
        compositeDisposable.add(apiMonAn.getMonAnMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        monAnMoiModel -> {
                            if (monAnMoiModel.isSuccess())
                            {
                                monAnMoiList = monAnMoiModel.getResult();
                                monAnMoiAdapter = new MonAnMoiAdapter(getApplicationContext(), monAnMoiList);
                                mrvQuanLi.setAdapter(monAnMoiAdapter);
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
                                loaiMonAnList.add(new LoaiMonAn ("8","Thống kê"));
                                loaiMonAnList.add(new LoaiMonAn("9","Thoát"));
                                //khoi tao adapter
                                loaiMonAnAdapter = new LoaiMonAnAdapter(getApplicationContext(), loaiMonAnList);
                                mlvTrang.setAdapter(loaiMonAnAdapter);
                            }
                        }
                ));
    }
    private void initView(){
        mbtnThem = (ImageButton) findViewById(R.id.btnThemSp);
        mrvQuanLi = (RecyclerView) findViewById(R.id.rvQuanLi);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mrvQuanLi.setHasFixedSize(true);
        mrvQuanLi.setLayoutManager(layoutManager);
        mngvTrang = findViewById(R.id.ngvTrang);
        mlvTrang = findViewById(R.id.lvTrang);
        mdlTrang = findViewById(R.id.dlTrang);
        mtbQuanLi = findViewById(R.id.tbQuanLi);
        loaiMonAnList = new ArrayList<>();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Sửa")){
            suaSanPham();
        }else if (item.getTitle().equals("Xóa")){
            xoaSanPham();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaSanPham() {
        compositeDisposable.add(apiMonAn.xoaSanPham(sanPhamSuaXoa.getMaMonAn())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongBaoModel -> {
                            if (thongBaoModel.isThanhcong()){
                                Toast.makeText(getApplicationContext(),thongBaoModel.getThongbao(),Toast.LENGTH_LONG).show();
                                getMonAn();
                            }else {
                                Toast.makeText(getApplicationContext(),thongBaoModel.getThongbao(),Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Log.d("log", throwable.getMessage());
                        }
                ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(),ThemSP.class);
        intent.putExtra("sua", sanPhamSuaXoa);
        startActivity(intent);
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

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if (event != null){
            sanPhamSuaXoa = event.getMonAnMoi();
        }
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