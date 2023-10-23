package com.phucthinh.ungdungdatthucan.model;

import java.util.Date;

public class Item {
    int MaMonAn;
    String TenMonAn;
    int SoLuong;
    String HinhAnhMonAn;
    String GiaMonAn;
    Date NgayMua;
    String TongTien;

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhAnhMonAn() {
        return HinhAnhMonAn;
    }

    public void setHinhAnhMonAn(String hinhAnhMonAn) {
        HinhAnhMonAn = hinhAnhMonAn;
    }

    public String getGiaMonAn() {
        return GiaMonAn;
    }

    public void setGiaMonAn(String giaMonAn) {
        GiaMonAn = giaMonAn;
    }

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date ngayMua) {
        NgayMua = ngayMua;
    }
}


