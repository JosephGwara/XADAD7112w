package com.groupfour.khwakhanyawelfare.presentation.onboarding.beneficiary_details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentBeneficiaryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BeneficiaryDetailsFragment : Fragment() {

    private val viewModel: BeneficiaryDetailsViewModel by viewModels()
    private lateinit var binding:FragmentBeneficiaryDetailsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeneficiaryDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNeedSelector()
    }


    private fun setupNeedSelector(){
        val myAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.donation_types)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.needSelect.setAdapter(myAdapter)
    }
}