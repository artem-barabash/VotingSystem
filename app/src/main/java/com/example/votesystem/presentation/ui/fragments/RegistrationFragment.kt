package com.example.votesystem.presentation.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.votesystem.R
import com.example.votesystem.data.FireBaseManager
import com.example.votesystem.databinding.FragmentRegistrationBinding
import com.example.votesystem.domain.entities.Answer
import com.example.votesystem.domain.entities.User
import com.example.votesystem.presentation.viewmodel.LoginViewModel
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    private val binding get() = _binding

    private val sharedViewModel: LoginViewModel by activityViewModels()

    private val fireBaseManager = FireBaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return  root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragment = this@RegistrationFragment
        }

        binding!!.datePickerButton.setOnClickListener {
            changeDate()
        }

        binding!!.buttonCancel.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    fun getDataForRegistration(){


        if(binding!!.textEmailAddress.text.toString().isNotEmpty()
            && binding!!.editFirstName.text.toString().isNotEmpty()
            && binding!!.editLastName.text.toString().isNotEmpty()
            && binding!!.textViewDate.text.toString().isNotEmpty()){
            val gender:String =
                if(binding?.radioButtonMale?.isChecked == true) "MALE" else "FEMALE"
            val arrayList = ArrayList<Answer>()

            val user = User("first", binding!!.textEmailAddress.text.toString(),binding!!.editFirstName.text.toString(),
                     binding!!.editLastName.text.toString(),binding!!.textViewDate.text.toString(),
                "","", gender, "",  arrayList)
            fireBaseManager.addUser(user)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun changeDate(){
        val date: Calendar = Calendar.getInstance()
        var thisAYear = date.get(Calendar.YEAR).toInt()
        var thisAMonth = date.get(Calendar.MONTH).toInt()
        var thisADay = date.get(Calendar.DAY_OF_MONTH).toInt()

        val dpd = DatePickerDialog(this.requireContext(),
            DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
                thisAMonth = thisMonth + 1
                thisADay = thisDay
                thisAYear = thisYear

                binding?.textViewDate!!.text = "$thisDay.$thisAMonth.$thisYear"
                dateBirthday = LocalDate.of(thisAYear, thisAMonth, thisADay)
                println(dateBirthday.toString())

                val newDate: Calendar = Calendar.getInstance()
                newDate.set(thisYear, thisMonth, thisDay)
                //mh.entryDate = newDate.timeInMillis // setting new date
            }, thisAYear, thisAMonth, thisADay)



        dpd.show()
    }

    companion object {
        lateinit var dateBirthday: LocalDate
    }
}