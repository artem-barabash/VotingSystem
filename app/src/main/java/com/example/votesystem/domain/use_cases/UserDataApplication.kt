package com.example.votesystem.domain.use_cases

import android.app.Application
import com.example.votesystem.data.room.AppDatabase


class UserDataApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}