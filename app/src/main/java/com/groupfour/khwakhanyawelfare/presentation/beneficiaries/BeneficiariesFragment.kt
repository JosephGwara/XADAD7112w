package com.groupfour.khwakhanyawelfare.presentation.beneficiaries

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
        //layout params edited here due to xml config not working as expected
        val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            MATCH_PARENT, MATCH_PARENT
        )
        binding.root.layoutParams = params
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBeneficiaries()
        setupObservers()
    }

    private fun getBeneficiaries(){
        toggleProgressBar(true)
        viewModel.getBeneficiaries()
    }

    private fun toggleProgressBar(visible:Boolean){
        binding.progressBar.isVisible = visible
    }

    private fun setupObservers(){
        viewModel.beneficiariesList.observe(viewLifecycleOwner){ users ->

            if (users != null){
                toggleProgressBar(false)
                val adapter = BeneficiariesAdapter(users)
                binding.beneficiariesRecyclerView.adapter = adapter
                binding.beneficiariesRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            }
        }
    }
}