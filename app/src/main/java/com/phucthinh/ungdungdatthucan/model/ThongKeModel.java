package com.phucthinh.ungdungdatthucan.model;

import java.util.List;

public class ThongKeModel {
    boolean thanhcong;
    String thongbao;
    List<ThongKe> ketqua;

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

    public List<ThongKe> getKetqua() {
        return ketqua;
    }

    public void setKetqua(List<ThongKe> ketqua) {
        this.ketqua = ketqua;
    }
}
