package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.ungdungdatthucan.Interface.ItemClickListener;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.activity.ChiTietActivity;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;

import java.text.DecimalFormat;
import java.util.List;

public class UuDaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MonAnMoi> monAnMoiList;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public UuDaiAdapter(Context context, List<MonAnMoi> monAnMoiList) {
        this.context = context;
        this.monAnMoiList = monAnMoiList;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uu_dai, parent, false);
            return new MyViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder)
        {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            MonAnMoi monAnMoi = monAnMoiList.get(position);
            myViewHolder.mtvTen.setText(monAnMoi.getTenMonAn().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.mtvGia.setText("Giá: " + decimalFormat.format(Double.parseDouble(monAnMoi.getGiaMonAn().trim())) + "đ");
            myViewHolder.mtvMoTa.setText(monAnMoi.getMoTaMonAn().trim());
            Glide.with(context).load(monAnMoi.getHinhAnhMonAn()).into(myViewHolder.mimgHinhAnh);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClik) {
                    if (!isLongClik)
                    {
                        //click
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet", monAnMoi);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
        else
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return monAnMoiList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return monAnMoiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mtvTen, mtvGia, mtvMoTa;
        ImageView mimgHinhAnh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvTen = itemView.findViewById(R.id.itemUuDai_tvTen);
            mtvGia = itemView.findViewById(R.id.itemUuDai_tvGia);
            mtvMoTa = itemView.findViewById(R.id.itemUuDai_tvMoTa);
            mimgHinhAnh = itemView.findViewById(R.id.itemUuDai_imgHinhAnh);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }
    }
}
