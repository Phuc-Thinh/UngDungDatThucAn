package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {

    Toolbar mtbThongKe;
    PieChart mpcThongKe;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiMonAn apiMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        initView();
        actionToolbar();
        getdataChart();
    }

    private void getdataChart() {
        List<PieEntry> listData = new ArrayList<>();
        compositeDisposable.add(apiMonAn.getThongKe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if (thongKeModel.isThanhcong()){
                                for (int i = 0; i<thongKeModel.getKetqua().size();i++){
                                    String tenMonAn = thongKeModel.getKetqua().get(i).getTenMonAn();
                                    int tong = thongKeModel.getKetqua().get(i).getTong();
                                    listData.add(new PieEntry(tong,tenMonAn));
                                }
                                PieDataSet pieDataSet = new PieDataSet(listData,"Thống kê");
                                PieData data = new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter(mpcThongKe));
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                mpcThongKe.setData(data);
                                mpcThongKe.animateXY(2000,2000);
                                mpcThongKe.setUsePercentValues(true);
                                mpcThongKe.getDescription().setEnabled(false);
                                mpcThongKe.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("log",throwable.getMessage());
                        }
                ));
    }

    private void initView() {
        mtbThongKe = findViewById(R.id.tbThongKe);
        mpcThongKe = findViewById(R.id.pcThongKe);
    }

    private void actionToolbar() {
        setSupportActionBar(mtbThongKe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbThongKe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}