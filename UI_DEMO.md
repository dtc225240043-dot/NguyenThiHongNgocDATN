#  UI/UX Demo - Chức năng Quản lý Tài khoản

## Screen 1️⃣: HomeScreen (với Hamburger Menu)

```
┌─────────────────────────────────────────────────┐
│ ☰     Xin chào, username          Logout      │ ← TopBar (xanh dương)
├─────────────────────────────────────────────────┤
│                                                  │
│   Nội dung HomeTab (Học bài)                  │
│  - Danh sách bài học                           │
│  - Topics                                      │
│  - Quiz quizzes                                │
│  - v.v...                                      │
│                                                  │
│                                                  │
├─────────────────────────────────────────────────┤
│ Học  Từ vựng  Quiz  Stats  AI                   │ ← Bottom Navigation
└─────────────────────────────────────────────────┘

Khi user nhấp menu ☰ → Drawer mở từ trái
```

---

## Screen 1B️⃣: Drawer Menu (khi mở ☰)

```
┌────────────────────────────┐
│ ★★★ DRAWER MENU ★★★      │
├────────────────────────────┤
│                            │
│         icon             │ ← Avatar
│     username               │ ← Username
│                            │
├────────────────────────────┤
│                            │
│   Thông tin tài khoản    │ ← Menu item 1
│                            │
│   Đổi mật khẩu           │ ← Menu item 2
│                            │
├────────────────────────────┤
│                            │


(Phía ngoài drawer)
HomeScreen content vẫn hiển thị phía sau,
transparency/scrim effect
```

---

## Screen 2️⃣: ViewUserInfoScreen (Xem Thông tin)

```
┌─────────────────────────────────────────────────┐
│ ◀     Thông tin tài khoản                      │ ← TopBar (xanh)
├─────────────────────────────────────────────────┤
│                                                  │
│ ┌──────────────────────────────────────────┐   │
│ │  Tên tài khoản                         │   │ ← Card
│ │ • abc_user_123                           │   │
│ │ ─────────────────────────────────────    │   │
│ │  Mục tiêu học                          │   │
│ │ • Đạt trình độ HSK 3                     │   │
│ │ ─────────────────────────────────────    │   │
│ │  Trình độ                               │   │
│ │ • HSK 1                                  │   │
│ │ ─────────────────────────────────────    │   │
│ │ ⏰ Thời gian học                          │   │
│ │ • 1 giờ mỗi ngày                         │   │
│ │ ─────────────────────────────────────    │   │
│ │  Độ tuổi                                │   │
│ │ • 25-35                                  │   │
│ │ ─────────────────────────────────────    │   │
│ │  Cách học yêu thích                    │   │
│ │ • Video + Quiz                           │   │
│ └──────────────────────────────────────────┘   │
│                                                  │
│ ┌──────────────────────────────────────────┐   │
│ │      ◀ Quay lại (OutlinedButton)        │   │
│ └──────────────────────────────────────────┘   │
│                                                  │
└─────────────────────────────────────────────────┘
```

---

## Screen 3️⃣: ChangePasswordScreen (Đổi Mật khẩu)

```
┌─────────────────────────────────────────────────┐
│ ◀     Đổi mật khẩu                            │ ← TopBar (xanh)
├─────────────────────────────────────────────────┤
│                                                  │
│   ┌────────────────────────────────────────┐   │
│   │ Nhập mật khẩu mới                      │   │ ← Card
│   │                                        │   │
│   │  [Mật khẩu mới...]                   │   │ ← OutlinedTextField
│   │                                        │   │
│   │  [Xác nhận mật khẩu...]              │   │ ← OutlinedTextField
│   └────────────────────────────────────────┘   │
│                                                  │
│ ┌────────────────────────────────────────────┐  │
│ │ ✅ Đổi mật khẩu thành công!               │  │ ← Success message
│ └────────────────────────────────────────────┘  │ (green box)
│                                                  │
│ ┌──────────────────────────────────────────┐   │
│ │   Cập nhật mật khẩu (Button)           │   │
│ └──────────────────────────────────────────┘   │
│                                                  │
│ ┌──────────────────────────────────────────┐   │
│ │    ◀ Quay lại (OutlinedButton)           │   │
│ └──────────────────────────────────────────┘   │
│                                                  │
└─────────────────────────────────────────────────┘

States:
- Idle: Empty form, " Cập nhật mật khẩu" button enabled
- Loading: CircularProgressIndicator inside button
- Success: ✅ green message, button disabled, form cleared
- Error: ❌ red message, button enabled, form kept
```

---

## Interaction Flow

### Flow 1: Xem Thông tin Tài khoản

```
HomeScreen
    ↓ [User nhấp ☰]
Drawer mở
    ↓ [User select " Thông tin tài khoản"]
Drawer đóng (auto-close)
    ↓ [Navigation trigger]
ViewUserInfoScreen tải
    ↓ [Load từ DB]
Hiển thị Card với 6 fields
    ↓ [User nhấp "◀ Quay lại"]
Back to HomeScreen
```

### Flow 2: Đổi Mật khẩu

```
HomeScreen
    ↓ [User nhấp ☰]
Drawer mở
    ↓ [User select " Đổi mật khẩu"]
Drawer đóng (auto-close)
    ↓ [Navigation trigger]
ChangePasswordScreen tải
    ↓ [User nhập data]
    ↓ [User nhấp " Cập nhật"]
VALIDATION:
  - Empty check → Error ❌
  - Length check (< 4) → Error ❌
  - Match check (≠) → Error ❌
  - All pass → Submit to DB
    ↓ [DB update]
    ↓ [Loading indicator visible]
    ↓ [DB success or error]
Success message ✅
    ↓ [User nhấp "◀ Quay lại"]
Back to HomeScreen
(or auto-close after success)
```

---

## Color & Style Reference

### Colors
```
Primary:  #1976D2 (Blue) — TopBar, Buttons, Icons
Success:  #2E7D32 (Green) — ✅ messages
Error:    #C62828 (Red)   — ❌ messages
Background: ⚪ #F5F5F5 (Light Gray) — Cards
Text Dark: ⚫ #000000 (Black) — Text
Text Light: ⚪ #666666 (Gray) — Labels
```

### Typography
```
Title: Material3.headlineMedium (24sp, bold)
Body: Material3.bodyMedium (16sp)
Label: Material3.labelMedium (12sp, gray)
```

### Spacing
```
Padding: 20-24 dp
Spacing: 8-16 dp
Card elevation: 4 dp
Border radius: 8 dp
```

---

## Component Behavior

### Drawer
- **Trigger**: Hamburger ☰ button in TopBar
- **Animation**: Slide from left (Material3 default)
- **Auto-close**: When menu item clicked
- **Scrim**: Semi-transparent overlay on background content

### Buttons
- **Primary ( Update)**: Button (filled, blue)
- **Secondary (◀ Back)**: OutlinedButton (stroke, outlined)
- **Disabled state**: Greyed out (--B0BEC5)

### Text Fields
- **Type**: OutlinedTextField
- **Icon**: Lock  on left
- **Validation**: Real-time visual feedback (border color)

### Messages
- **Success**: Green box, ✅ emoji + text
- **Error**: Red box, ❌ emoji + text
- **Duration**: Persistent until user changes input

---

## Responsive Behavior

- **Portrait**: Full width cards, centered buttons
- **Landscape**: Same layout (scrollable if needed)
- **Drawer width**: Fixed 280 dp (Material3 standard)
- **Text fields**: Max width with padding

---

## Accessibility

- Back button: Always accessible
- Icons: Have appropriate descriptions
- Colors: Not reliant on color alone (includes text + emoji)
- Contrast: Blue on white (WCAG AAA compliant)
- Keyboard: Native Compose keyboard support

---

**Design Version**: v1.0  
**Material Design**: Material3  
**Theme**: Light Mode (Blue + White)
