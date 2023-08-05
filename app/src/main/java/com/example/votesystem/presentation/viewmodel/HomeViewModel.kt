package com.example.votesystem.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.votesystem.data.FireBaseManager
import com.example.votesystem.data.room.UserDao
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.Question
import com.example.votesystem.domain.entities.User
import com.example.votesystem.domain.entities.Variant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*


class HomeViewModel(private val userAccount: User, private val userDao: UserDao): ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val fireBaseManager = FireBaseManager()

    init {
        _user.value = userAccount

        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllQuestions()
            userDao.deleteAllVariants()

            fireBaseManager.retrieveAllQuestion(userDao)
            fireBaseManager.retrieveAllVariants(userDao)
        }
    }

    fun showCardNumber(number: String?): String? {
        val arrNumber = number?.split("".toRegex())?.toTypedArray()
        val sb = StringBuilder()
        if (arrNumber != null) {
            for (i in arrNumber.indices) {
                sb.append(arrNumber[i])
                if (i % 4 == 0 && i != arrNumber.size - 1) sb.append(" ")
            }
        }
        return sb.toString()
    }

    fun getQuestionsAll() : Flow<List<Question>> = userDao.selectAllQuestions()

    fun getVariantsForQuestion(number: String) : Flow<List<Variant>> = userDao.selectAllVariants(number)

    fun addAnswer(numberKey: String?, numberQuestion: String, answer: String) {
        val date = Date()
        val timestamp = Timestamp(date.time)

        val answer = Answer(
            1,
            numberKey!!,
            numberQuestion,
            answer,
            timestamp.toString()
        )

        fireBaseManager.addAnswer(answer)
    }

}