#  Hướng dẫn sử dụng Chức năng Quản lý Tài khoản

##  Chức năng mới

Ứng dụng HSKMaster vừa được cập nhật với chức năng **Quản lý Tài khoản** tiện lợi. Người dùng có thể dễ dàng truy cập các chức năng này thông qua menu hamburger (3 thanh gạch).

---

##  Hướng dẫn chi tiết

### 1️⃣ Truy cập Menu Hamburger

- **Vị trí**: Góc trên trái màn hình HomeScreen (sau khi đăng nhập)
- **Cách sử dụng**: Nhấp vào biểu tượng **☰** (3 thanh gạch)
- **Kết quả**: Mở drawer menu với các tùy chọn quản lý tài khoản

### 2️⃣ Xem Thông tin Tài khoản ( Thông tin tài khoản)

**Chức năng**: Hiển thị đầy đủ thông tin cá nhân của người dùng

**Thông tin hiển thị**:
-  Tên tài khoản (username)
-  Mục tiêu học
-  Trình độ hiện tại
- ⏰ Thời gian học mỗi ngày
-  Độ tuổi
-  Cách học yêu thích

**Cách sử dụng**:
1. Mở menu hamburger ☰
2. Chọn ** Thông tin tài khoản**
3. Xem các thông tin được lưu trong hệ thống
4. Nhấp **◀ Quay lại** để trở về HomeScreen

### 3️⃣ Đổi Mật khẩu ( Đổi mật khẩu)

**Chức năng**: Cho phép người dùng thay đổi mật khẩu đăng nhập

**Yêu cầu**:
- Mật khẩu mới phải có ít nhất 4 ký tự
- Mật khẩu xác nhận phải trùng khớp với mật khẩu mới

**Cách sử dụng**:
1. Mở menu hamburger ☰
2. Chọn ** Đổi mật khẩu**
3. Nhập **Mật khẩu mới** trong trường đầu tiên
4. Nhập **Xác nhận mật khẩu** trong trường thứ hai
5. Nhấp ** Cập nhật mật khẩu**
6. Chờ thông báo xác nhận (✅ hoặc ❌)
7. Nhấp **◀ Quay lại** để trở về HomeScreen

**Thông báo**:
- ✅ **Đổi mật khẩu thành công!** — Mật khẩu đã được cập nhật
- ❌ **Vui lòng nhập đầy đủ thông tin** — Bỏ sót trường nào đó
- ❌ **Mật khẩu không trùng khớp** — Hai mật khẩu không giống nhau
- ❌ **Mật khẩu phải có ít nhất 4 ký tự** — Mật khẩu quá ngắn

---

##  Thiết kế Giao diện

### Menu Drawer
- **Màu sắc**: Xanh dương (Blue gradient) + trắng
- **Header**: Hiển thị avatar biểu tượng  + username
- **Mục**: 2 tùy chọn chính với icon
  -  Thông tin tài khoản
  -  Đổi mật khẩu

### Thông tin Tài khoản Screen
- **TopBar**: Tiêu đề " Thông tin tài khoản" + nút quay lại
- **Nội dung**: Card hiển thị từng thông tin với label và giá trị
- **Bố cục**: Vertically scrollable card list

### Đổi Mật khẩu Screen
- **TopBar**: Tiêu đề " Đổi mật khẩu" + nút quay lại
- **Form**: Centered card với 2 outlined text fields
- **Validation**: Hiển thị thông báo lỗi/thành công ngay dưới form
- **Nút**: 
  - Xanh dương ( Cập nhật mật khẩu)
  - Outlined (◀ Quay lại)

---

##  File được tạo/sửa

### New Files (Tệp mới):
- `components/AccountMenuDrawer.kt` — Component drawer + menu items
- `screens/ViewUserInfoScreen.kt` — Màn hình xem thông tin tài khoản

### Modified Files (Tệp sửa):
- `screens/HomeScreen.kt` — Thêm drawer, hamburger button
- `screens/ChangePasswordScreen.kt` — Redesign UI, thêm validation
- `MainActivity.kt` — Thêm route viewUserInfoScreen

---

## ✅ Các tính năng đã hoàn thành

- ✅ Hamburger menu (☰) ở góc trên trái
- ✅ Drawer menu hiển thị khi nhấp hamburger
- ✅ Xem thông tin tài khoản từ Database
- ✅ Đổi mật khẩu với validation
- ✅ Giao diện đẹp, responsive
- ✅ Thông báo lỗi/thành công hiển thị rõ ràng
- ✅ Tích hợp suôn sẻ với HomeScreen

---

##  Hướng dẫn chạy

1. **Build & Run**:
   ```bash
   ./gradlew assembleDebug
   # Hoặc click Run trong Android Studio
   ```

2. **Kiểm tra**:
   - Đăng nhập vào app
   - Nhấp biểu tượng ☰ ở TopBar trái
   - Chọn một trong 2 chức năng
   - Kiểm tra UI và chức năng

---

##  Lưu ý

- Tất cả thông tin được lưu trong **Room Database** local
- Khi đổi mật khẩu, nó được lưu ngay vào DB
- Thông tin tài khoản được load từ DB khi vào screen
- Menu drawer tự động đóng sau khi chọn một mục

---

**Phiên bản**: v1.0 - Account Management Feature  
**Ngày**: 16-05-2026  
**Trạng thái**: ✅ Ready to Use
