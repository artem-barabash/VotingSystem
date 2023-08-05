package com.example.votesystem.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.votesystem.domain.use_cases.UserDataApplication
import com.example.votesystem.databinding.FragmentVoteListBinding
import com.example.votesystem.domain.use_cases.UserFactory
import com.example.votesystem.presentation.adapter.QuestionAdapter
import com.example.votesystem.presentation.viewmodel.HomeViewModel
import com.example.votesystem.presentation.viewmodel.factory.HomeViewModelFactory
import kotlinx.coroutines.launch


class VoteListFragment : Fragment() {
    private var _binding: FragmentVoteListBinding? = null

    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter

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
        _binding = FragmentVoteListBinding.inflate(inflater, container, false)

        val root = binding!!.root

        recyclerView = binding!!.questionRecyclerView

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.getQuestionsAll().collect(){ it ->
                adapter = QuestionAdapter(recyclerView, it)
                recyclerView.adapter = adapter
            }

            ///TODO JOIN LEFT

            ///TODO якщо особа вже проголосувала то опитування не доступне

        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}