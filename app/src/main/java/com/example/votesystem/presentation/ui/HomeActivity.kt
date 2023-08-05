package com.example.votesystem.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.votesystem.R
import com.example.votesystem.databinding.ActivityHomeBinding
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.User
import com.example.votesystem.domain.use_cases.UserFactory
import com.example.votesystem.presentation.ui.fragments.HomeFragment
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.BIRTHDAY
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.EMAIL
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.FIRST_NAME
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.GENDER
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.HOME_ADDRESS
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.LAST_NAME
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.NUMBER_KEY
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.PASSWORD
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.PHONE
import com.example.votesystem.presentation.ui.fragments.LoginFragment.Companion.TEMP_USER_DATA
import com.example.votesystem.presentation.ui.fragments.VoteListFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)

        getUserData()

        binding.bottomNavigationBar.setOnItemSelectedListener {


            when (it.itemId){
                R.id.itemHome -> replaceFragment(HomeFragment())
                R.id.itemVoteList -> replaceFragment(VoteListFragment())
                //R.id.itemHistory -> replaceFragment()
            }

            return@setOnItemSelectedListener true
        }

        replaceFragment(HomeFragment())


        binding.swiperefresh.setOnRefreshListener {
            val i = Intent(this@HomeActivity, HomeActivity::class.java)
            finish()
            overridePendingTransition(1, 1)
            startActivity(i)
        }
    }

    private fun getUserData() {
        sharedPreferences = this.getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        val user = User(
            sharedPreferences.getString(NUMBER_KEY, "").toString(),
            sharedPreferences.getString(FIRST_NAME, "").toString(),
            sharedPreferences.getString(LAST_NAME, "").toString(),
            sharedPreferences.getString(EMAIL, "").toString(),
            sharedPreferences.getString(PHONE, "").toString(),
            sharedPreferences.getString(PASSWORD, "").toString(),
            sharedPreferences.getString(BIRTHDAY, "").toString(),
            sharedPreferences.getString(GENDER, "").toString(),
            sharedPreferences.getString(HOME_ADDRESS, "").toString(),
            ArrayList<Answer>()
        )

        UserFactory(user)

    }

    private fun replaceFragment(selected: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_layout, selected)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}