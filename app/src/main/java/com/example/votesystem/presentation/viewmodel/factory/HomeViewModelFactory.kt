package com.example.votesystem.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.votesystem.data.room.UserDao
import com.example.votesystem.domain.entities.User
import com.example.votesystem.presentation.viewmodel.HomeViewModel


class HomeViewModelFactory(private val user: User, private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(user, userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}