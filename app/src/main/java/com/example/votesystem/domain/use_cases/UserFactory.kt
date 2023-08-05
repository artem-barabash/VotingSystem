package com.example.votesystem.domain.use_cases

import com.example.votesystem.domain.entities.User

class UserFactory(private val user: User) {
    init {
        ACCOUNT = user
    }

    companion object{
        lateinit var ACCOUNT:User
    }
}