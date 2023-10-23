package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.DonHangAdapter;
import com.phucthinh.ungdungdatthucan.model.DonHangModel;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    RecyclerView mrcvDonHang;
    Toolbar mtbDonHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);
        initView();
        initToolbar();
        getDonHang();

    }


    private void getDonHang() {
        compositeDisposable.add(apiMonAn.xemDonHang(Utils.khachHang_current.getMaKH(),Utils.khachHang_current.getMaQuyen())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(), donHangModel.getKetqua());
                            mrcvDonHang.setAdapter(adapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập để xem đơn hàng.", Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    private void initToolbar() {
        setSupportActionBar(mtbDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        mrcvDonHang = findViewById(R.id.rcvDonHang);
        mtbDonHang = findViewById(R.id.tbDonHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvDonHang.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}