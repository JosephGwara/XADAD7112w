package com.groupfour.khwakhanyawelfare.presentation.beneficiaries

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentBeneficiariesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeneficiariesFragment : Fragment() {

    private val viewModel: BeneficiariesViewModel by viewModels()
    private lateinit var binding:FragmentBeneficiariesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeneficiariesBinding.inflate(layoutInflater)
        return binding.root
    }
}