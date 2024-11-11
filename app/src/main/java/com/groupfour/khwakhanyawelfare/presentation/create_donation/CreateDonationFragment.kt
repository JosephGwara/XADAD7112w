package com.groupfour.khwakhanyawelfare.presentation.create_donation

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.data.models.Donation
import com.groupfour.khwakhanyawelfare.databinding.FragmentCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNeedSelector()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.beneficiary.observe(viewLifecycleOwner){ user ->
            if (user != null){
                val donationType = binding.needSelectChoice.text.toString()
                val message = binding.messageEditText.text.toString()
                val currentTimestamp = System.currentTimeMillis()
                viewModel.createDonation(donationType = donationType, recipientName = user.name, recipientEmail = user.email,message = message, timestamp = currentTimestamp )
            }
        }
        viewModel.donation.observe(viewLifecycleOwner){ donation ->
            if (donation != null){
                toggleProgressBar(false)
                donation.recipientEmail?.let { viewModel.deleteDocument(it) }
                displaySuccessDialogue(donation)
            }

        }
    }
    private fun displaySuccessDialogue(donation: Donation){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("A donation has been made to ${donation.recipientName} ")
            setMessage("The donation was made at ${donation.timestamp?.let { convertLongToTime(it) }}")
            setCancelable(false)
            setPositiveButton("Ok"){ _, _ ->
            }
        }.create().show()
    }
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    private fun initListeners() {
     binding.donateBtn.setOnClickListener {
           createDonation()
     }
    }
    private fun createDonation(){
        viewModel.getBeneficiaries(binding.needSelectChoice.text.toString())
        toggleProgressBar(true)
    }

    private fun setupNeedSelector(){
        val myAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            resources.getStringArray(R.array.donation_types)
        )
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.needSelectChoice.setAdapter(myAdapter)
    }
    private fun toggleProgressBar(visible:Boolean){
        binding.progressBar.isVisible = visible
    }
}