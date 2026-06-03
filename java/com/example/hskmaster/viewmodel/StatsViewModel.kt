// StatsViewModel.kt
package com.example.hskmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hskmaster.data.local.entity.UserStatsEntity
import com.example.hskmaster.data.repository.StatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsViewModel(
    private val repository: StatsRepository
) : ViewModel() {

    private val _userStats = MutableStateFlow(UserStatsEntity(id = 1))
    val userStats: StateFlow<UserStatsEntity> = _userStats.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeStatsIfNeeded()
        }
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            repository.getUserStats().collect { stats ->
                _userStats.value = stats ?: UserStatsEntity(id = 1)
            }
        }
    }

    fun addXP(amount: Int = 10) {
        viewModelScope.launch {
            repository.addXP(amount)
        }
    }

    fun addLessons(amount: Int = 1) {
        viewModelScope.launch {
            repository.addLessons(amount)
        }
    }

    fun addQuizzes(amount: Int = 1) {
        viewModelScope.launch {
            repository.addQuizzes(amount)
        }
    }

    fun updateStreak() {
        viewModelScope.launch {
            repository.updateStreak()
        }
    }

    fun addStudyTime(minutes: Int) {
        viewModelScope.launch {
            repository.addStudyTime(minutes)
        }
    }

    fun updateLevel(newLevel: Int) {
        viewModelScope.launch {
            val current = _userStats.value
            repository.updateStats(current.copy(level = newLevel))
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            repository.resetStats()
        }
    }
}