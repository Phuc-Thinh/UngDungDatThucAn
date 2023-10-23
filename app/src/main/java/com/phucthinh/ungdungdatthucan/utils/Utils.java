package com.phucthinh.ungdungdatthucan.utils;

import com.phucthinh.ungdungdatthucan.model.GioHang;
import com.phucthinh.ungdungdatthucan.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://10.1.144.225/ungdungdatthucan/";
    public static List<GioHang> mangGioHang;
    public static List<GioHang> mangGioHangThanhToan = new ArrayList<>();
    public static KhachHang khachHang_current = new KhachHang();
}
