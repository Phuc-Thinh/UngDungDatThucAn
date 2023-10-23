package com.phucthinh.ungdungdatthucan.model;

import java.util.Date;
import java.util.List;

public class DonHang {
    int MaDH;
    int MaKH;
    String DiaChi;
    String TongTien;
    String TenKH;

    Date NgayMua;
    List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public int getMaDH() {
        return MaDH;
    }

    public void setMaDH(int maDH) {
        MaDH = maDH;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        MaKH = maKH;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date ngayMua) {
        NgayMua = ngayMua;
    }
}
