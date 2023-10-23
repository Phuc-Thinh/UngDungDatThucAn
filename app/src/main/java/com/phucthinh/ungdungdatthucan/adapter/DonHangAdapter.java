package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.DonHang;

import java.text.SimpleDateFormat;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    private List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.mtvDonHang.setText("Đơn hàng: " + donHang.getMaDH());
        holder.mtvKhachHang.setText("Người đặt: " + donHang.getTenKH());
        holder.mtvDiaChi.setText("Địa chỉ: " + donHang.getDiaChi());
        holder.mtvTong.setText("Tổng cộng: " + donHang.getTongTien() + "đ");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(donHang.getNgayMua());
        holder.mtvNgayMua.setText("Ngày mua: " + formattedDate);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.mrcvChiTietDonHang.getContext(), LinearLayoutManager.VERTICAL, false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(context, donHang.getItem());
        holder.mrcvChiTietDonHang.setLayoutManager(layoutManager);
        holder.mrcvChiTietDonHang.setAdapter(chiTietAdapter);
        holder.mrcvChiTietDonHang.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {

        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mtvDonHang, mtvDiaChi, mtvTong, mtvKhachHang, mtvNgayMua;
        RecyclerView mrcvChiTietDonHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvDonHang = itemView.findViewById(R.id.tvDonHang);
            mtvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            mtvTong = itemView.findViewById(R.id.tvTong);
            mtvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            mtvKhachHang = itemView.findViewById(R.id.tvKhachHang);
            mrcvChiTietDonHang = itemView.findViewById(R.id.rcvChiTietDonHang);
        }
    }
}
