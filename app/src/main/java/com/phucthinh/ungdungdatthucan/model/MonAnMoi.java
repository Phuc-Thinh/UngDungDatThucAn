package com.phucthinh.ungdungdatthucan.model;

import java.io.Serializable;

public class MonAnMoi implements Serializable {
    int MaMonAn;
    String TenMonAn;
    String GiaMonAn;
    String HinhAnhMonAn;
    String MoTaMonAn;
    int MaLoaiMonAn;

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

    public String getGiaMonAn() {
        return GiaMonAn;
    }

    public void setGiaMonAn(String giaMonAn) {
        GiaMonAn = giaMonAn;
    }

    public String getHinhAnhMonAn() {
        return HinhAnhMonAn;
    }

    public void setHinhAnhMonAn(String hinhAnhMonAn) {
        HinhAnhMonAn = hinhAnhMonAn;
    }

    public String getMoTaMonAn() {
        return MoTaMonAn;
    }

    public void setMoTaMonAn(String moTaMonAn) {
        MoTaMonAn = moTaMonAn;
    }

    public int getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(int maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }
}
