package com.example.votesystem.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.Question
import com.example.votesystem.domain.entities.Variant
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllQuestions(listQuestion: List<Question>)

    @Query("SELECT * FROM questions")
    fun selectAllQuestions(): Flow<List<Question>>

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllVariants(listQuestion: List<Variant>)

    @Query("SELECT * FROM variants WHERE number_question = :number")
    fun selectAllVariants(number: String): Flow<List<Variant>>

    @Query("DELETE FROM variants")
    fun deleteAllVariants()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllAnswers(list: List<Answer>)

    @Query("SELECT * FROM answers WHERE number_question = :number")
    fun selectAllAnswers(number: String): Flow<List<Answer>>

    @Query("DELETE FROM answers")
    fun deleteAllAnswers()
}