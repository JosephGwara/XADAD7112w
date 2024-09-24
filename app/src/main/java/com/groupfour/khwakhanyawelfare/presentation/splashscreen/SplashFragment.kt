package com.groupfour.khwakhanyawelfare.presentation.splashscreen

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groupfour.khwakhanyawelfare.databinding.FragmentSplashBinding
import com.groupfour.khwakhanyawelfare.presentation.auth.AuthActivity
import com.groupfour.khwakhanyawelfare.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkAuthStatus()
    }

    override fun onResume() {
        super.onResume()
        initObservers()
    }
    private fun initObservers(){
        viewModel.userAuthenticated.observe(viewLifecycleOwner){ authenticated ->
            when(authenticated){
                true -> navigateToHome()
                false -> navigateToSignIn()
            }
        }
    }

    private fun navigateToSignIn() {
        startActivity(Intent(requireActivity(),AuthActivity::class.java))
    }

    private fun navigateToHome(){
        startActivity(Intent(requireActivity(),HomeActivity::class.java))
    }

}