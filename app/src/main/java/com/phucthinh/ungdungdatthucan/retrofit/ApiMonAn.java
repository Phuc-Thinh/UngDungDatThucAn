package com.phucthinh.ungdungdatthucan.retrofit;

import com.phucthinh.ungdungdatthucan.model.DonHangModel;
import com.phucthinh.ungdungdatthucan.model.KhachHangModel;
import com.phucthinh.ungdungdatthucan.model.LoaiMonAnModel;
import com.phucthinh.ungdungdatthucan.model.MonAnMoiModel;
import com.phucthinh.ungdungdatthucan.model.ThongBaoModel;
import com.phucthinh.ungdungdatthucan.model.ThongKeModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiMonAn {
    @GET("layloaimonan.php")
    Observable<LoaiMonAnModel> getLoaiMonAn();

    @GET("laymonan.php")
    Observable<MonAnMoiModel> getMonAnMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<MonAnMoiModel> getMonAn(
            @Field("page") int page,
            @Field("MaLoaiMonAn") int MaLoaiMonAn
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<KhachHangModel> dangKy(
            @Field("TenKH") String TenKH,
            @Field("DiaChi") String DiaChi,
            @Field("SoDienThoai") String SoDienThoai,
            @Field("Email") String Email,
            @Field("MatKhau") String MatKhau,
            @Field("MaQuyen") int MaQuyen
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<KhachHangModel> dangNhap(
            @Field("Email") String Emai,
            @Field("MatKhau") String MatKhau

    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<KhachHangModel> taoDonHang(
            @Field("Email") String Emai,
            @Field("TongTien") String TongTien,
            @Field("MaKH") int MaKH,
            @Field("DiaChi") String DiaChi,
            @Field("SoLuong") int SoLuong,
            @Field("ChiTiet") String ChiTiet,
            @Field("NgayMua") String currentTime
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("MaKH") int maKH,
            @Field("MaQuyen") int maQuyen
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<MonAnMoiModel> timKiem(
            @Field("timkiem") String timKiem
    );

    @POST("themsanpham.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> themSanPham(
            @Field("TenMonAn") String tenMonAn,
            @Field("GiaMonAn") String giaMonAn,
            @Field("HinhAnhMonAn") String hinhAnhMonAn,
            @Field("MoTaMonAn") String moTaMonAn,
            @Field("MaLoaiMonAn") int maLoaiMonAn
    );

    @Multipart
    @POST("taihinh.php")
    Call<ThongBaoModel> taiFile(@Part MultipartBody.Part file);

    @POST("xoasanpham.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> xoaSanPham(
            @Field("MaMonAn") int maMonAn
    );

    @POST("suasanpham.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> suaSanPham(
            @Field("TenMonAn") String tenMonAn,
            @Field("GiaMonAn") String giaMonAn,
            @Field("HinhAnhMonAn") String hinhAnhMonAn,
            @Field("MoTaMonAn") String moTaMonAn,
            @Field("MaLoaiMonAn") int maLoaiMonAn,
            @Field("MaMonAn") int maMonAn
    );

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @GET("laykhachhang.php")
    Observable<KhachHangModel> layKhachHang();

    @POST("themkhachhang.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> themKhachHang(
            @Field("TenKH") String tenKH,
            @Field("SoDienThoai") String soDienThoai,
            @Field("Email") String email,
            @Field("DiaChi") String diaChi,
            @Field("MatKhau") String matKhau,
            @Field("MaQuyen") int maQuyen
    );

    @POST("suakhachhang.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> suaKhachHang(
            @Field("TenKH") String tenKH,
            @Field("SoDienThoai") String soDienThoai,
            @Field("Email") String email,
            @Field("DiaChi") String diaChi,
            @Field("MatKhau") String matKhau,
            @Field("MaQuyen") int maQuyen,
            @Field("MaKH") int maKH
    );

    @POST("suanguoidungkhachhang.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> suaNguoiDungKH(
            @Field("TenKH") String tenKH,
            @Field("SoDienThoai") String soDienThoai,
            @Field("Email") String email,
            @Field("DiaChi") String diaChi,
            @Field("MaKH") int maKH
    );

    @POST("xoakhachhang.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> xoaKH(
            @Field("MaKH") int maKH
    );

}
