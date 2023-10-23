package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.Item;

import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyviewHolder> {
    Context context;
    List<Item> itemList;

    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public ChiTietAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.mtvTensp.setText(item.getTenMonAn()+"");
        holder.mtvSoLuong.setText("Số lượng: " + item.getSoLuong() +"");
        holder.mtvGia.setText("Giá: "+item.getGiaMonAn()+"");
        Glide.with(context).load(item.getHinhAnhMonAn()).into(holder.mimgChiTietDonHang);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView mimgChiTietDonHang;
        TextView mtvTensp, mtvSoLuong, mtvGia;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            mimgChiTietDonHang = itemView.findViewById(R.id.imgChiTietDonHang);
            mtvTensp = itemView.findViewById(R.id.tvTensp);
            mtvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            mtvGia = itemView.findViewById(R.id.tvGia);
        }
    }
}
