package com.phucthinh.ungdungdatthucan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.adapter.UuDaiAdapter;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;
import com.phucthinh.ungdungdatthucan.retrofit.ApiMonAn;
import com.phucthinh.ungdungdatthucan.retrofit.RetrofitClient;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimKiemActivity extends AppCompatActivity {
    Toolbar mtbTimKiem;
    RecyclerView mrcvTimKiem;
    EditText medtTimKiem;
    ApiMonAn apiMonAn;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UuDaiAdapter uuDaiAdapter;
    List<MonAnMoi>monAnMoiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        initView();
        actionToolBar();
    }

    private void initView() {
        monAnMoiList = new ArrayList<>();
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);

        mtbTimKiem = findViewById(R.id.tbTimKiem);
        mrcvTimKiem = findViewById(R.id.rcvTimKiem);
        medtTimKiem = findViewById(R.id.edtTimKiem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvTimKiem.setHasFixedSize(true);
        mrcvTimKiem.setLayoutManager(layoutManager);
        medtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                {
                    monAnMoiList.clear();
                    uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), monAnMoiList);
                    mrcvTimKiem.setAdapter(uuDaiAdapter);
                }
                else
                {
                    getDataSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataSearch(String s) {
        monAnMoiList.clear();
        compositeDisposable.add(apiMonAn.timKiem(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        monAnMoiModel -> {
                            if (monAnMoiModel.isSuccess())
                            {
                                monAnMoiList = monAnMoiModel.getResult();
                                uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), monAnMoiList);
                                mrcvTimKiem.setAdapter(uuDaiAdapter);
                            } else
                            {
                                Toast.makeText(getApplicationContext(), monAnMoiModel.getMessage(), Toast.LENGTH_SHORT).show();
                                monAnMoiList.clear();
                                uuDaiAdapter.notifyDataSetChanged();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void actionToolBar() {
        setSupportActionBar(mtbTimKiem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbTimKiem.setNavigationOnClickListener(new View.OnClickListener() {
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