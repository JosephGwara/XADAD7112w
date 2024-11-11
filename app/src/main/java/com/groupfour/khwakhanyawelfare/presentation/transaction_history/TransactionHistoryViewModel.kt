package com.groupfour.khwakhanyawelfare.presentation.transaction_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.groupfour.khwakhanyawelfare.data.models.Donation
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(private val firestore: FirebaseFirestore) : ViewModel() {

    private var _donationList = MutableLiveData<List<Donation>>()
    val donationList: LiveData<List<Donation>> get() = _donationList


    fun getDonations() = viewModelScope.launch(Dispatchers.IO){
        firestore.collection(Constants.FIREBASE_DONATION_COLLECTION)
            .get()
            .addOnSuccessListener { documents ->
                val donationList = mutableListOf<Donation>()
                for (document in documents){
                    val  donation = document.toObject(Donation::class.java)
                    donationList.add(donation)
                }
                _donationList.postValue(donationList)

            }
            .addOnFailureListener {
                Timber.tag("JosephGwara").d("Error Fetching data Transactions VM")
            }
    }
}