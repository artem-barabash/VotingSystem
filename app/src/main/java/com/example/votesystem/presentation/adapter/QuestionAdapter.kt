package com.example.votesystem.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.votesystem.R

import com.example.votesystem.domain.entities.Question
import com.example.votesystem.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.example.votesystem.presentation.ui.fragments.VotePageFragment
import com.example.votesystem.presentation.ui.fragments.VotePageFragment.Companion.PAGE
import com.google.firebase.database.*

class QuestionAdapter(
    private val view: View,
    private val dataset:List<Question?>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>(){


    class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text:TextView = view.findViewById(R.id.textQuestion)
        val status:TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.question_item,
            parent, false)

        return QuestionViewHolder(adapterLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = dataset[position]

        holder.text.text = question!!.textQuestion

       //holder.status.setText(if(question.isOpen)
            //R.string.open_status else R.string.close_status)

        holder.status.text = view.context.
        resources.getString(if(question.isOpen) R.string.open_status
        else R.string.close_status)

        holder.status.setTextColor(if(question.isOpen)
            Color.rgb(0, 128, 0)
            else Color.rgb( 211, 211, 211))

        println("question=$question")

        holder.itemView.setOnClickListener {
            if(question.isOpen){
                openQuestion(question, ACCOUNT.numberKey)
            }

        }
    }

    private fun openQuestion(question: Question, numberKey: String?) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

        val answerRef =  databaseReference.child("Voting/Answer")

        answerRef.orderByChild("userNumberKey").equalTo(numberKey)
            //.orderByChild("userNumberKey").equalTo(numberKey)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot!!.exists()){
                        for(itemSnapshot in snapshot.children){

                            val currentNumberQuestion = itemSnapshot.child("numberQuestion")
                                .getValue(String::class.java)!!

                            if(currentNumberQuestion == question.number){
                                Toast.makeText(view.context, "You were voted!", Toast.LENGTH_SHORT).show()
                                //break
                            }else{
                                val bundle = Bundle()
                                bundle.putParcelable(PAGE, question)
                                enterToFinishFragment(bundle)

                            }
                        }

                    }else{
                        val bundle = Bundle()
                        bundle.putParcelable(PAGE, question)
                        enterToFinishFragment(bundle)

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    println(error.message)
                }
            })



    }

    private fun enterToFinishFragment(bundle: Bundle) {
        val fragmentManager = (view.context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        val fragment = VotePageFragment()
        fragment.arguments = bundle

        transactionFragment.replace(R.id.fl_layout, fragment)

        transactionFragment.commit()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}