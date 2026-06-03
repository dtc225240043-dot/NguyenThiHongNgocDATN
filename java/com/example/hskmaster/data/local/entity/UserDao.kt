package com.example.hskmaster.data.local.dao

import androidx.room.*
import com.example.hskmaster.data.local.entity.UserEntity

@Dao
interface UserDao {

    // Đăng ký người dùng
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(user: UserEntity)

    // Kiểm tra xem người dùng đã tồn tại hay chưa
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun checkUserExists(username: String): UserEntity?

    // Đăng nhập người dùng
    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): UserEntity?

    // Cập nhật mật khẩu
    @Query("UPDATE user SET password = :newPassword WHERE username = :username")
    suspend fun changePassword(username: String, newPassword: String)

    // Cập nhật mục tiêu học của người dùng
    @Query("UPDATE user SET goal = :goal WHERE username = :username")
    suspend fun updateGoal(username: String, goal: String)

    // Cập nhật trình độ của người dùng
    @Query("UPDATE user SET currentLevel = :level WHERE username = :username")
    suspend fun updateLevel(username: String, level: String)

    // Cập nhật toàn bộ thông tin người dùng
    @Update
    suspend fun updateUserInfo(user: UserEntity)

    // Lấy 1 user bất kỳ (dùng cho trường hợp test / dev khi chưa có session)
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getAnyUser(): UserEntity?
}