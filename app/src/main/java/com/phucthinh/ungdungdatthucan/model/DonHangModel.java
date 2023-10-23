package com.phucthinh.ungdungdatthucan.model;

import java.util.List;

public class DonHangModel {
    boolean thanhcong;
    String thongbao;
    List<DonHang> ketqua;

    public List<DonHang> getKetqua() {
        return ketqua;
    }

    public void setKetqua(List<DonHang> ketqua) {
        this.ketqua = ketqua;
    }

    public boolean isThanhcong() {
        return thanhcong;
    }

    public void setThanhcong(boolean thanhcong) {
        this.thanhcong = thanhcong;
    }

    public String getThongbao() {
        return thongbao;
    }

    public void setThongbao(String thongbao) {
        this.thongbao = thongbao;
    }


}
