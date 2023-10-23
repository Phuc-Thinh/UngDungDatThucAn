package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.ungdungdatthucan.Interface.ItemClickListener;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.activity.ChiTietActivity;
import com.phucthinh.ungdungdatthucan.model.EventBus.SuaXoaEvent;
import com.phucthinh.ungdungdatthucan.model.MonAnMoi;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class MonAnMoiAdapter extends RecyclerView.Adapter<MonAnMoiAdapter.MyViewHolder>{
    Context context;
    List<MonAnMoi> array;

    public MonAnMoiAdapter(Context context, List<MonAnMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_mon_an, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MonAnMoi monAnMoi = array.get(position);
        holder.mtvTenMonAn.setText(monAnMoi.getTenMonAn());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mtvGiaMonAn.setText("Giá: " + decimalFormat.format(Double.parseDouble(monAnMoi.getGiaMonAn().trim())) + "đ");
        if (monAnMoi.getHinhAnhMonAn().contains("http")){
            Glide.with(context).load(monAnMoi.getHinhAnhMonAn()).into(holder.mimgHinhAnhMonAn);
        }else{
            String hinh = Utils.BASE_URL + "hinhanh/" + monAnMoi.getHinhAnhMonAn();
            Glide.with(context).load(hinh).into(holder.mimgHinhAnhMonAn);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClik) {
                if (!isLongClik)
                {
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", monAnMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(monAnMoi));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        TextView mtvGiaMonAn, mtvTenMonAn;
        ImageView mimgHinhAnhMonAn;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvGiaMonAn = itemView.findViewById(R.id.item_tvGia);
            mtvTenMonAn = itemView.findViewById(R.id.item_tvTenMonAn);
            mimgHinhAnhMonAn = itemView.findViewById(R.id.item_imgMonAn);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"Sửa");
            menu.add(0,1,getAdapterPosition(),"Xóa");
        }

        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
