package com.groupfour.khwakhanyawelfare.presentation.create_donation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDonationFragment : Fragment() {


    private val viewModel: CreateDonationViewModel by viewModels()
    private lateinit var binding:FragmentCreateDonationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDonationBinding.inflate(layoutInflater)
        return binding.root
    }
}