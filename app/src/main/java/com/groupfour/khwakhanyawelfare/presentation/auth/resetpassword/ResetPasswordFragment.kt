package com.groupfour.khwakhanyawelfare.presentation.auth.resetpassword

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private val viewModel: ResetPasswordViewModel by viewModels()
    private lateinit var binding:FragmentResetPasswordBinding

    /*
    *  TODO Implement reset password functionality
    *
    * */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }
}