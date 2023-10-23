package com.phucthinh.ungdungdatthucan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.ungdungdatthucan.R;
import com.phucthinh.ungdungdatthucan.model.LoaiMonAn;

import java.util.List;

public class LoaiMonAnAdapter extends BaseAdapter {

    Context context;
    List<LoaiMonAn> loaiMonAnList;

    public LoaiMonAnAdapter(Context context, List<LoaiMonAn> loaiMonAnList) {
        this.context = context;
        this.loaiMonAnList = loaiMonAnList;
    }

    @Override
    public int getCount() {
        return loaiMonAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView mTenMonAn;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_mon_an, null);
            viewHolder.mTenMonAn = view.findViewById(R.id.item_ten_mon_an);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTenMonAn.setText(loaiMonAnList.get(i).getTenLoaiMonAn());
        return view;

    }
}

