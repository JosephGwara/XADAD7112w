package com.groupfour.khwakhanyawelfare.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.groupfour.khwakhanyawelfare.R
import com.groupfour.khwakhanyawelfare.databinding.ActivityHomeBinding
import com.groupfour.khwakhanyawelfare.presentation.beneficiaries.BeneficiariesFragment
import com.groupfour.khwakhanyawelfare.presentation.transaction_history.TransactionHistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setDefaultNavPosition()
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.beneficiaries -> {
                    loadFragment(BeneficiariesFragment())
                    true
                }

                R.id.donation_history -> {
                    loadFragment(TransactionHistoryFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }

        setContentView(binding.root)
    }

    private fun setDefaultNavPosition() {
        loadFragment(HomeFragment())
        binding.bottomNav.selectedItemId = R.id.home
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_screen, fragment)
        transaction.commit()
    }
}