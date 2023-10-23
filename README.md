# UngDungDatThucAn
# Ứng dụng đặt thức ăn 

## Mô tả

Dự án này là một ứng dụng di động Android cho phép người dùng đặt thức ăn từ cửa hàng.

## Yêu Cầu

- Android Studio (phiên bản 4.2 trở lên)
- XAMPP (phiên bản 8.0.28 / PHP 8.0.28 trở lên dành cho máy Windows)

## Cài Đặt

1. **Cài đặt XAMPP** ([XAMPP](https://www.apachefriends.org/download.html))
- Chi tiết cài đặt XAMPP xem trong file Word

2. **Mở Dự Án Trong Android Studio**
- Mở Android Studio
- Chọn `Open an existing Android Studio project`
- Đến thư mục chứa mã nguồn và chọn Folder *UngDungDatThucAn*

3. **Cấu Hình Kết Nối Cơ Sở Dữ Liệu**

- Mở XAMPP và bấm Start vào 2 mục Apache và MySQL.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/Xampp1.png?ref_type=heads)

- Bấm vào mục Admin của MySQL để khởi động phpMyAdmin.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/phpAdmin.png?ref_type=heads)

- Tạo 1 database mới và đặt tên *ungdungdatthucan*.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/NewDB.png?ref_type=heads)

- Import cơ sở dữ liệu từ tệp `ungdungdatthucan.sql` vào phpMyAdmin.Kéo xuống cuối cùng và nhấn nút Import.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/AddDB.png?ref_type=heads)

- Màn hình khi Import cơ sở dữ liệu thành công.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/AddDBSuccess.png?ref_type=heads)

- Vào thư mục chứa XAMPP mà bạn đã chọn để cài đặt và mở thư mục *htdocs*.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/HtDocs.png?ref_type=heads)

- Sau đó thêm thư mục *ungdungdatthucan* gồm các file php vào.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/themphp.png?ref_type=heads)
- 
4. **Cấu Hình API Endpoint**
- Mở Command Prompt trên máy tính của bạn và gõ lệnh ipconfig.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/ipconfig.png?ref_type=heads)

- Kéo xuống phần **Wireless LAN adapter Wi-Fi** và copy địa chỉ của *IPv4 Address*
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/IPAddress.png?ref_type=heads)

- Trong mã nguồn, mở `Utils.java` và thay đổi địa chỉ đường dẫn ứng với máy tính của bạn.
![Alt text](https://gitlab.com/noinho1202/doannganh/-/raw/master/Ultis.png?ref_type=heads)

5. **Chạy Ứng Dụng**
- Kết nối thiết bị Android hoặc sử dụng máy ảo.
- Bấm nút "Run" trong Android Studio để chạy ứng dụng trên thiết bị đã kết nối.

## Sử Dụng

- Mở ứng dụng trên thiết bị Android của bạn.
- Đăng nhập hoặc đăng ký tài khoản mới.
- Bắt đầu đặt thức ăn từ danh sách nhà hàng có sẵn.

## Ghi Chú

- Đảm bảo rằng XAMPP đã được cài đặt và đang chạy để cung cấp máy chủ MySQL.
- Đảm bảo rằng bạn đã đổi địa chỉ đường dẫn tương ứng với máy tính của bạn.

## Tác Giả

- Tên: Vũ Huy Hoàng - Nguyễn Phúc Thịnh
- Email: [2051012031hoang@ou.edu.vn - 2051012110thinh@ou.edu.vn]

---


