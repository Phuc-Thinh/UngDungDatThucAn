package com.phucthinh.ungdungdatthucan.model;

public class LoaiMonAn {
    int MaLoaiMonAn;
    String TenLoaiMonAn;

    public LoaiMonAn(String maLoaiMonAn, String tenLoaiMonAn) {
        this.MaLoaiMonAn = Integer.parseInt(String.valueOf(maLoaiMonAn));
        this.TenLoaiMonAn = tenLoaiMonAn;
    }

    public int getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(int maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }

    public String getTenLoaiMonAn() {
        return TenLoaiMonAn;
    }

    public void setTenLoaiMonAn(String tenLoaiMonAn) {
        TenLoaiMonAn = tenLoaiMonAn;
    }
}
