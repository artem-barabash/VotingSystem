package com.example.votesystem.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.votesystem.R
import com.example.votesystem.databinding.FragmentLoginBinding
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.User
import com.example.votesystem.presentation.ui.HomeActivity
import com.example.votesystem.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding

    private val sharedViewModel: LoginViewModel by activityViewModels()

    private lateinit var textEmail:EditText
    private lateinit var textPassword:EditText

    private lateinit var textMistake: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding  = FragmentLoginBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragment = this@LoginFragment
        }

        sharedPreferences = requireContext().getSharedPreferences(TEMP_USER_DATA,
            AppCompatActivity.MODE_PRIVATE
        )


        textEmail = binding!!.editTextEmail
        textPassword = binding!!.editTextPassword
        textMistake = binding!!.textMistake

        binding!!.btnLogin.setOnClickListener {
            if(textEmail.text.toString().isNotEmpty()
                && textPassword.text.toString().isNotEmpty()){

                val email = textEmail.text.toString()
                val password = textPassword.text.toString()

                val auth:FirebaseAuth = FirebaseAuth.getInstance()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            //val intent = Intent(requireContext(), HomeActivity::class.java)
                            //requireContext().startActivity(intent)
                            getUserDataInFireBase(email,password)
                        }
                    }.addOnFailureListener{
                        textMistake.setText(R.string.failure_auth)
                    }
            }else{
                textMistake.setText(R.string.message_empty_fields)
            }
        }


        binding!!.btnRegister.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun getUserDataInFireBase(email: String, password: String){
        val dataReferenceUser: DatabaseReference = FirebaseDatabase.getInstance()
            .reference.child("User")


        val query: Query = dataReferenceUser.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("WrongConstant", "ApplySharedPref")
            override  fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {

                    val list = ArrayList<Answer>()
                    val key = it.key.toString()

                    val user = User(
                        key,
                        it.child("firstName").value.toString(),
                        it.child("lastName").value.toString(),
                        email,
                        it.child("phone").value.toString(),
                        password,
                        it.child("birthday").value.toString(),
                        it.child("gender").value.toString(),
                        it.child("homeAddress").value.toString(),
                        list
                    )

                    lifecycleScope.coroutineContext.let {
                        sharedPreferences.edit().clear().apply()

                        saveDataSharedPreferences(user)
                    }

                    val intent = Intent(requireContext(), HomeActivity::class.java)

                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }


    fun saveDataSharedPreferences(user: User){
        val editor = sharedPreferences.edit()

        editor.putString(NUMBER_KEY, user.numberKey)
        editor.putString(FIRST_NAME, user.firstName)
        editor.putString(LAST_NAME, user.lastName)
        editor.putString(PHONE, user.phone)
        editor.putString(EMAIL, user.email)
        editor.putString(PASSWORD, user.password)
        editor.putString(BIRTHDAY, user.birthday)
        editor.putString(GENDER, user.gender)
        editor.putString(HOME_ADDRESS, user.homeAddress)


        editor.commit()
    }

    companion object {
        const val TEMP_USER_DATA : String = "MySharedPref"

        const val FIRST_NAME: String = "firstNameU"
        const val LAST_NAME: String = "lastNameU"
        const val PHONE: String = "phoneU"
        const val EMAIL: String = "emailU"
        const val BIRTHDAY: String = "birthdayU"
        const val GENDER: String = "genderU"
        const val HOME_ADDRESS: String = "homeAddressU"
        const val PASSWORD: String = "passwordU"
        const val NUMBER_KEY: String = "numberKeyU"
    }
}