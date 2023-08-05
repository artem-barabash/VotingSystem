package com.example.votesystem.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.votesystem.domain.use_cases.UserDataApplication
import com.example.votesystem.databinding.FragmentHomeBinding
import com.example.votesystem.domain.use_cases.UserFactory
import com.example.votesystem.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.example.votesystem.presentation.viewmodel.HomeViewModel
import com.example.votesystem.presentation.viewmodel.factory.HomeViewModelFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private val sharedViewModel: HomeViewModel by activityViewModels{
        HomeViewModelFactory(
            UserFactory.ACCOUNT,
            (activity?.application as UserDataApplication).database.userDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding!!.root


        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //textView = view.findViewById(R.id.text1)

        //textView.text = ACCOUNT.firstName + " " + ACCOUNT.lastName
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding!!.textViewBank.text = "Bank account : " + sharedViewModel.showCardNumber(ACCOUNT.numberKey)
        binding!!.textViewName.text = "Name: " + sharedViewModel.user.value!!.firstName +
         " " + sharedViewModel.user.value!!.lastName
        binding!!.textViewBirthday.text = "Birthday: " + sharedViewModel.user.value!!.birthday
        binding!!.textViewGender.text = "Gender: " + sharedViewModel.user.value!!.gender
        binding!!.textViewEmail.text = "Email: " + sharedViewModel.user.value!!.email
        binding!!.textViewPhone.text = "Phone: " + sharedViewModel.user.value!!.phone
        binding!!.textViewHomeAddress.text = "Home Address: " + sharedViewModel.user.value!!.homeAddress
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}