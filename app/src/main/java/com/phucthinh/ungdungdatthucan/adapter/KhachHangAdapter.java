package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.ungdungdatthucan.Interface.ItemClickListener;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.activity.ChiTietKhachHang;
import com.phucthinh.ungdungdatthucan.model.EventBus.SuaXoaKhachHangEvent;
import com.phucthinh.ungdungdatthucan.model.KhachHang;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<KhachHang> khachHangList;
    public KhachHangAdapter(Context context, List<KhachHang> khachHangList) {
        this.context = context;
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        KhachHang khachHang = khachHangList.get(position);
        myViewHolder.mtvTenKH.setText(khachHang.getTenKH().trim());
        myViewHolder.mtvQuyen.setText(String.valueOf(khachHang.getMaQuyen()));
        if(khachHang.getMaQuyen() == 1){
            myViewHolder.mtvQuyen.setText("Quản trị viên");
        }else{
            myViewHolder.mtvQuyen.setText("Khách hàng");
        }
//
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClik) {
                if (!isLongClik)
                {
                    //click
                    Intent intent = new Intent(context, ChiTietKhachHang.class);
                    intent.putExtra("chitietkhachhang",khachHang);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new SuaXoaKhachHangEvent(khachHang));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        TextView mtvTenKH, mtvQuyen;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvTenKH = itemView.findViewById(R.id.tvTenKH);
            mtvQuyen = itemView.findViewById(R.id.tvQuyen);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"Xoá");
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }

    }
}
