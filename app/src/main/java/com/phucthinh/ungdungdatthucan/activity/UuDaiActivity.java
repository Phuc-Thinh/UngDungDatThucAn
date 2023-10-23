package com.phucthinh.ungdungdatthucan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class UuDaiActivity extends AppCompatActivity {
    Toolbar mtbUuDai;
    RecyclerView mrcvUuDai;
    ApiMonAn apiMonAn;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int MaLoaiMonAn;
    UuDaiAdapter uuDaiAdapter;
    List<MonAnMoi> monAnMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uu_dai);
        apiMonAn = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiMonAn.class);
        MaLoaiMonAn = getIntent().getIntExtra("MaLoaiMonAn",1);

        initView();
        actionBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        mrcvUuDai.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false)
                {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == monAnMoiList.size() - 1)
                    {
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                monAnMoiList.add(null);
                uuDaiAdapter.notifyItemInserted(monAnMoiList.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remove null
                monAnMoiList.remove(monAnMoiList.size() - 1);
                uuDaiAdapter.notifyItemRemoved(monAnMoiList.size());
                page = page + 1;
                getData(page);
                uuDaiAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiMonAn.getMonAn(page, MaLoaiMonAn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        monAnMoiModel -> {
                            if (monAnMoiModel.isSuccess())
                            {
                                if (uuDaiAdapter == null)
                                {
                                    monAnMoiList = monAnMoiModel.getResult();
                                    uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), monAnMoiList);
                                    mrcvUuDai.setAdapter(uuDaiAdapter);
                                }
                                else
                                {
                                    int vt = monAnMoiList.size() - 1;
                                    int sladd = monAnMoiModel.getResult().size();
                                    for (int i = 0; i <sladd; i++)
                                    {
                                        monAnMoiList.add(monAnMoiModel.getResult().get(i));
                                    }
                                    uuDaiAdapter.notifyItemRangeInserted(vt,sladd);
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Hết món ăn rồi", Toast.LENGTH_SHORT).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server", Toast.LENGTH_SHORT).show();
                        }
                ));
    }


    private void actionBar() {
        setSupportActionBar(mtbUuDai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (MaLoaiMonAn == 2)
        {
            actionBar.setTitle("Món Mới");
        } else if (MaLoaiMonAn == 3)
        {
            actionBar.setTitle("Combo 1 Người");
        }
        else if (MaLoaiMonAn == 4)
        {
            actionBar.setTitle("Combo Nhóm");
        }
        else if (MaLoaiMonAn == 5)
        {
            actionBar.setTitle("Burger - Cơm - Mì Ý");
        } else if (MaLoaiMonAn == 6)
        {
            actionBar.setTitle("Thức Uống & Tráng Miệng");
        }
        mtbUuDai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mtbUuDai = findViewById(R.id.tbUuDai);
        mrcvUuDai = findViewById(R.id.rcvUuDai);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mrcvUuDai.setLayoutManager(linearLayoutManager);
        mrcvUuDai.setHasFixedSize(true);
        monAnMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}