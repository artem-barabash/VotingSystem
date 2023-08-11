package com.example.votesystem.data

import com.example.votesystem.data.room.UserDao
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.Question
import com.example.votesystem.domain.entities.Variant
import com.example.votesystem.domain.entities.User
import com.google.firebase.database.*
import java.util.ArrayList

class FireBaseManager {
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addUser(user: User) {
        val userDatabaseReference = databaseReference.child("User")

        userDatabaseReference.child(user.numberKey).setValue(user)
    }

    fun retrieveAllQuestion(userDao: UserDao){
        val questionRef = databaseReference.child("Voting/Question")
        val list = ArrayList<Question>()

        var id = 1

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {


                    for (h in snapshot.children) {

                        val isOpen = h.child("open").value.toString() == "true"
                        list.add(
                            Question(
                                id,
                                h.child("number").value.toString(),
                                h.child("textQuestion").value.toString(),
                                isOpen,
                                h.child("time").value.toString()
                            )
                        )
                        id++
                    }
                }

                userDao.insertAllQuestions(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    fun retrieveAllVariants(userDao: UserDao){
        val questionRef = databaseReference.child("Voting/Variant")
        val list = ArrayList<Variant>()

        var id = 1

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {


                    for (h in snapshot.children) {
                        list.add(
                            Variant(
                                id,
                                h.child("numberQuestion").value.toString(),
                                h.child("text").value.toString()
                            )
                        )
                        id++
                    }
                }

                userDao.insertAllVariants(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }

    fun addAnswer(answer: Answer){
        val answerDatabaseReference = databaseReference.child("Voting/Answer")

        answerDatabaseReference
            .child(answer.userNumberKey + "-" + answer.dateTime.replace('.', ','))
            .setValue(answer)
    }

    fun retrieveAllAnswers(userDao: UserDao){
        val answersRef = databaseReference.child("Voting/Answer")
        val list = ArrayList<Answer>()

        var id = 1

        answersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {


                    for (h in snapshot.children) {
                        list.add(
                            Answer(
                                id,
                                h.child("userNumberKey").value.toString(),
                                h.child("numberQuestion").value.toString(),
                                h.child("textAnswer").value.toString(),
                                h.child("dateTime").value.toString()
                            )
                        )
                        id++
                    }
                }

                userDao.insertAllAnswers(list)
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })
    }
}