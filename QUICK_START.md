#  QUICK START - Chức năng Quản lý Tài khoản

## ⏱️ 30 Giây Bắt Đầu

### Bước 1: Build APK
```bash
cd C:\Users\ngoc2\AndroidStudioProjects\HSKMaster
./gradlew :app:assembleDebug
```
✅ APK được tạo tại: `app/build/outputs/apk/debug/app-debug.apk`

### Bước 2: Cài đặt
```bash
./gradlew :app:installDebug
```
Hoặc: Kéo APK vào emulator

### Bước 3: Chạy App
- Mở app HSKMaster
- Đăng nhập (nếu chưa có tài khoản, đăng ký trước)

### Bước 4: Test Chức năng
1. Sau khi đăng nhập thành công → HomeScreen
2. **Nhấp ☰** (biểu tượng 3 thanh gạch) ở góc trên trái
3. Drawer menu sẽ mở → Chọn một trong 2 chức năng:
   -  **Thông tin tài khoản** — Xem info
   -  **Đổi mật khẩu** — Thay đổi password

---

##  File APK

**Location**: 
```
C:\Users\ngoc2\AndroidStudioProjects\HSKMaster\app\build\outputs\apk\debug\app-debug.apk
```

**Size**: ~3-4 MB  
**Permissions**: Internet, Camera (nếu có video), Storage  

---

##  Test Cases

### Test Case 1: Mở Menu
- [ ] Vào HomeScreen sau đăng nhập
- [ ] Nhấp ☰ button ở TopBar trái
- [ ] ✅ Drawer menu mở từ trái
- [ ] Nhấp ☰ lại → ✅ Drawer đóng

### Test Case 2: Xem Thông tin Tài khoản
- [ ] Mở Drawer menu
- [ ] Nhấp " Thông tin tài khoản"
- [ ] ✅ Screen chuyển sang ViewUserInfoScreen
- [ ] ✅ Hiển thị 6 fields: username, goal, level, study time, age, learning method
- [ ] Nhấp "◀ Quay lại" → ✅ Quay về HomeScreen

### Test Case 3: Đổi Mật khẩu - Hợp lệ
- [ ] Mở Drawer menu
- [ ] Nhấp " Đổi mật khẩu"
- [ ] ✅ Screen chuyển sang ChangePasswordScreen
- [ ] Nhập mật khẩu: `test1234`
- [ ] Nhập xác nhận: `test1234`
- [ ] Nhấp " Cập nhật mật khẩu"
- [ ] ✅ Thông báo: "✅ Đổi mật khẩu thành công!"
- [ ] ✅ Có thể logout và login lại với mật khẩu mới

### Test Case 4: Đổi Mật khẩu - Lỗi (quá ngắn)
- [ ] Mở Drawer → " Đổi mật khẩu"
- [ ] Nhập mật khẩu: `ab`
- [ ] Nhập xác nhận: `ab`
- [ ] Nhấp " Cập nhật"
- [ ] ✅ Lỗi: "❌ Mật khẩu phải có ít nhất 4 ký tự"

### Test Case 5: Đổi Mật khẩu - Lỗi (không trùng khớp)
- [ ] Mở Drawer → " Đổi mật khẩu"
- [ ] Nhập mật khẩu: `password123`
- [ ] Nhập xác nhận: `password456`
- [ ] Nhấp " Cập nhật"
- [ ] ✅ Lỗi: "❌ Mật khẩu không trùng khớp"

### Test Case 6: Đổi Mật khẩu - Lỗi (trống)
- [ ] Mở Drawer → " Đổi mật khẩu"
- [ ] Không nhập gì
- [ ] Nhấp " Cập nhật"
- [ ] ✅ Lỗi: "❌ Vui lòng nhập đầy đủ thông tin"

---

##  File Created (Tóm tắt)

```
NEW:
├── components/AccountMenuDrawer.kt (Drawer component)
└── screens/ViewUserInfoScreen.kt (View user info screen)

MODIFIED:
├── screens/HomeScreen.kt (Added drawer + hamburger)
├── screens/ChangePasswordScreen.kt (Redesigned + validation)
└── MainActivity.kt (Added viewUserInfo route)

DOCS:
├── ACCOUNT_MANAGEMENT_GUIDE.md (User guide)
├── IMPLEMENTATION_SUMMARY.md (Dev summary)
├── UI_DEMO.md (UI/UX screenshots text)
└── QUICK_START.md (This file)
```

---

##  Mục tiêu Hoàn thành

✅ Hamburger menu (3 thanh gạch)  
✅ Drawer navigation  
✅ Xem thông tin tài khoản  
✅ Đổi mật khẩu + validation  
✅ Giao diện đẹp (Material3)  
✅ Build successful (no errors)  
✅ Ready for production  

---

##  Troubleshooting

### Vấn đề: App không build
```bash
# Xóa cache
./gradlew clean

# Build lại
./gradlew :app:assembleDebug
```

### Vấn đề: Drawer không mở
- Kiểm tra có pass `drawerState` vào drawer?
- Kiểm tra hamburger button onClick có gọi `drawerState.open()`?

### Vấn đề: ViewUserInfoScreen show "Không thể tải thông tin"
- Kiểm tra có user trong DB?
- Kiểm tra username parameter truyền đúng không?

### Vấn đề: Validation không hoạt động
- Kiểm tra logic validation trong ChangePasswordScreen
- Kiểm tra database.userDao().changePassword() có exist không?

---

##  Support

Nếu gặp lỗi, hãy check:
1. Log từ Android Studio (Logcat)
2. File IMPLEMENTATION_SUMMARY.md (Dev reference)
3. File ACCOUNT_MANAGEMENT_GUIDE.md (Feature guide)
4. Các file source code trong `app/src/main/java/.../screens/` và `components/`

---

##  Next Steps (Nâng cao)

1. **Thêm Edit User Info**: Cập nhật thông tin cá nhân
2. **Profile Picture**: Upload ảnh avatar
3. **Settings Screen**: Cài đặt ngôn ngữ, theme, notifications
4. **Two-Factor Auth**: Xác thực 2 lớp
5. **Security Audit**: Encrypt passwords, validate SSL

---

**Status**: ✅ READY TO TEST  
**Date**: 16-05-2026  
**Version**: v1.0
