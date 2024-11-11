package com.groupfour.khwakhanyawelfare.presentation.transaction_history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentTransactionHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment() {


    private val viewModel: TransactionHistoryViewModel by viewModels()
    private lateinit var binding:FragmentTransactionHistoryBinding


    //TODO SHOW all Donations Made by the user
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionHistoryBinding.inflate(layoutInflater)
        //layout params edited here due to xml config not working as expected
        val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            MATCH_PARENT, MATCH_PARENT
        )
        binding.root.layoutParams = params
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDonations()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.donationList.observe(viewLifecycleOwner){ donations ->
            if (donations != null){
                toggleProgressBar(false)
                val adapter = TransactionHistoryAdapter(donations)
                binding.donationHistoryRecyclerview.adapter = adapter
                binding.donationHistoryRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
            }

        }
    }

    private fun getDonations(){
        toggleProgressBar(true)
        viewModel.getDonations()
    }

    private fun toggleProgressBar(visible:Boolean){
        binding.progressBar.isVisible = visible
    }

}