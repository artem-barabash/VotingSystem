package com.example.votesystem.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.votesystem.R
import com.example.votesystem.databinding.FragmentHistoryBinding
import com.example.votesystem.databinding.FragmentHomeBinding
import com.example.votesystem.domain.use_cases.UserDataApplication
import com.example.votesystem.domain.use_cases.UserFactory
import com.example.votesystem.presentation.viewmodel.HomeViewModel
import com.example.votesystem.presentation.viewmodel.factory.HomeViewModelFactory


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding

    private val sharedViewModel: HomeViewModel by activityViewModels{
        HomeViewModelFactory(
            UserFactory.ACCOUNT,
            (activity?.application as UserDataApplication).database.userDao()
        )
    }

    lateinit var votingRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root = binding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }
}