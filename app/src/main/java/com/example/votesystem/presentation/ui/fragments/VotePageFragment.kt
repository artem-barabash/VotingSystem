package com.example.votesystem.presentation.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.votesystem.R
import com.example.votesystem.databinding.FragmentVoteListBinding
import com.example.votesystem.databinding.FragmentVotePageBinding
import com.example.votesystem.domain.entities.Question
import com.example.votesystem.domain.entities.Variant
import com.example.votesystem.domain.use_cases.UserDataApplication
import com.example.votesystem.domain.use_cases.UserFactory
import com.example.votesystem.domain.use_cases.UserFactory.Companion.ACCOUNT
import com.example.votesystem.presentation.adapter.VariantsAdapter
import com.example.votesystem.presentation.viewmodel.HomeViewModel
import com.example.votesystem.presentation.viewmodel.factory.HomeViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class VotePageFragment : Fragment() {

    private var _binding: FragmentVotePageBinding? = null

    private val binding get() = _binding

    lateinit var question: Question

    lateinit var container: LinearLayout

    private lateinit var radioGroup: RadioGroup

    private val sharedViewModel: HomeViewModel by activityViewModels{
        HomeViewModelFactory(
            UserFactory.ACCOUNT,
            (activity?.application as UserDataApplication).database.userDao()
        )
    }


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        question = arguments?.getParcelable(PAGE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentVotePageBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.textCaptionPage.text = question.textQuestion

        container = binding!!.variantsContainer

        binding!!.buttonOk.setOnClickListener {
            addVote()
        }

        binding!!.buttonCancel.setOnClickListener {
            cancelFragment()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.getVariantsForQuestion(question.number).collect(){
                createRadioButton(it)
            }
        }

    }

    private fun addVote() {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if(selectedRadioButtonId != -1){
            val radioButton: RadioButton = requireView().findViewById(selectedRadioButtonId)

            val answer = radioButton.text.toString()

            sharedViewModel.addAnswer(
                ACCOUNT.numberKey,
                question.number,
                answer
            )
            cancelFragment()

            Toast.makeText(requireContext(), "You voted successful!" , Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun createRadioButton(data: List<Variant>){
        radioGroup = RadioGroup(requireContext())
        radioGroup.orientation = RadioGroup.VERTICAL

        radioGroup.setBackgroundResource(R.color.white)

        for(variant in data.reversed()){
            val radioButton = RadioButton(requireContext())
            radioButton.text = variant.text

            radioButton.id = View.generateViewId()

            radioGroup.addView(radioButton)
        }



        container.addView(radioGroup)
    }

    private fun cancelFragment(){
        val fragmentManager = (requireContext() as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        val fragment = VoteListFragment()

        transactionFragment.replace(R.id.fl_layout, fragment)

        transactionFragment.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PAGE = "PAGE"
    }
}