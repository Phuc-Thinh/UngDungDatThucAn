package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.ungdungdatthucan.Interface.IImageClickListener;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.EventBus.TinhTongEvent;
import com.phucthinh.ungdungdatthucan.model.GioHang;
import com.phucthinh.ungdungdatthucan.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder>{
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.mtvitemGioHangTenMonAn.setText(gioHang.getTenMonAn());
        holder.mtvitemGioHangSoLuong.setText(gioHang.getSoLuong() + " ");
        Glide.with(context).load(gioHang.getHinhAnhMonAn()).into(holder.mimgitemGioHang);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mtvitemGioHangGiaMonAn.setText(decimalFormat.format(gioHang.getGiaMonAn()) + "đ");
        long price = gioHang.getSoLuong() * gioHang.getGiaMonAn();
        holder.mtvitemGioHangTongTien.setText(decimalFormat.format(price) + "đ");
        holder.mchkitemGioHangCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Utils.mangGioHang.get(holder.getAdapterPosition()).setChecked(true);
                    if (!Utils.mangGioHangThanhToan.contains(gioHang))
                    {
                        Utils.mangGioHangThanhToan.add(gioHang);
                    };
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
                else
                {
                    Utils.mangGioHang.get(holder.getAdapterPosition()).setChecked(false);
                    for (int i = 0; i <Utils.mangGioHangThanhToan.size(); i++)
                    {
                        if (Utils.mangGioHangThanhToan.get(i).getMaMonAn() == gioHang.getMaMonAn())
                        {
                            Utils.mangGioHangThanhToan.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });

        holder.mchkitemGioHangCheck.setChecked(gioHang.isChecked());

        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int position, int giaTri) {
                if (giaTri == 1)
                {
                    if (gioHangList.get(position).getSoLuong() > 1)
                    {
                        int newSize = gioHangList.get(position).getSoLuong() - 1;
                        gioHangList.get(position).setSoLuong(newSize);

                        holder.mtvitemGioHangSoLuong.setText(gioHangList.get(position).getSoLuong() + " ");
                        long price = gioHangList.get(position).getSoLuong() * gioHangList.get(position).getGiaMonAn();
                        holder.mtvitemGioHangTongTien.setText(decimalFormat.format(price) + "đ");
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    } else if (gioHangList.get(position).getSoLuong() == 1)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa món ăn này khỏi giỏ hàng?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Utils.mangGioHangThanhToan.remove(gioHang);
                                Utils.mangGioHang.remove(position);
                                Paper.book().write("giohang", Utils.mangGioHang);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
                else if (giaTri == 2)
                {
                    if (gioHangList.get(position).getSoLuong() < 11)
                    {
                        int newSize = gioHangList.get(position).getSoLuong() + 1;
                        gioHangList.get(position).setSoLuong(newSize);
                    }
                    holder.mtvitemGioHangSoLuong.setText(gioHangList.get(position).getSoLuong() + " ");
                    long price = gioHangList.get(position).getSoLuong() * gioHangList.get(position).getGiaMonAn();
                    holder.mtvitemGioHangTongTien.setText(decimalFormat.format(price) + "đ");
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mimgitemGioHang,mimgitemGioHangXoa,mimgitemGioHangThem;
        TextView mtvitemGioHangTenMonAn,mtvitemGioHangGiaMonAn,mtvitemGioHangSoLuong,mtvitemGioHangTongTien;
        IImageClickListener listener;
        CheckBox mchkitemGioHangCheck;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mimgitemGioHang = itemView.findViewById(R.id.img_itemGioHang);
            mimgitemGioHangXoa = itemView.findViewById(R.id.img_itemGioHangXoa);
            mimgitemGioHangThem = itemView.findViewById(R.id.img_itemGioHangThem);
            mtvitemGioHangTenMonAn = itemView.findViewById(R.id.tv_item_GioHangTenMonAn);
            mtvitemGioHangGiaMonAn = itemView.findViewById(R.id.tv_item_GioHangGiaMonAn);
            mtvitemGioHangSoLuong = itemView.findViewById(R.id.tv_item_GioHangSoLuong);
            mtvitemGioHangTongTien = itemView.findViewById(R.id.tv_item_GioHangTongTien);
            mchkitemGioHangCheck = itemView.findViewById(R.id.chk_item_GioHang);

            //event click
            mimgitemGioHangThem.setOnClickListener(this);
            mimgitemGioHangXoa.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v == mimgitemGioHangXoa)
            {
                //1 tru
                listener.onImageClick(v, getAdapterPosition(), 1);
            }
            else
            {
                //2 cong
                listener.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }
}
