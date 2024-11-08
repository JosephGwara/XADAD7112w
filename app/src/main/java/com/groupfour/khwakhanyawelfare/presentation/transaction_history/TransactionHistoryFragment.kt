package com.groupfour.khwakhanyawelfare.presentation.transaction_history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }
}