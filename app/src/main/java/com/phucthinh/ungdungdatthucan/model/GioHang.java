package com.phucthinh.ungdungdatthucan.model;

public class GioHang {
    int MaMonAn;
    String TenMonAn;
    long GiaMonAn;
    String HinhAnhMonAn;
    int SoLuong;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public GioHang()
    {

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

    public long getGiaMonAn() {
        return GiaMonAn;
    }

    public void setGiaMonAn(long giaMonAn) {
        GiaMonAn = giaMonAn;
    }

    public String getHinhAnhMonAn() {
        return HinhAnhMonAn;
    }

    public void setHinhAnhMonAn(String hinhAnhMonAn) {
        HinhAnhMonAn = hinhAnhMonAn;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
