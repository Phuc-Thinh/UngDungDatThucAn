package com.phucthinh.ungdungdatthucan.model.EventBus;

import com.phucthinh.ungdungdatthucan.model.KhachHang;

public class SuaXoaKhachHangEvent {
    KhachHang khachHang;

    public SuaXoaKhachHangEvent(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
}
