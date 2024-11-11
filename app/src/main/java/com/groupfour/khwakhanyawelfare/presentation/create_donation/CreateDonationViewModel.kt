package com.groupfour.khwakhanyawelfare.presentation.create_donation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupfour.khwakhanyawelfare.data.enums.DonationType
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.data.models.Donation
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateDonationViewModel @Inject constructor(private val firestore: FirebaseFirestore,private val firebaseAuth: FirebaseAuth) : ViewModel() {


    private var _beneficiary = MutableLiveData<User>()
    val beneficiary: LiveData<User> get() = _beneficiary

    private var _donation = MutableLiveData<Donation>()
    val donation: LiveData<Donation> get() = _donation

    fun getBeneficiaries(donationType: String) = viewModelScope.launch (Dispatchers.IO){
        firestore.collection(Constants.FIREBASE_USER_COLLECTION)
            .whereEqualTo("userType", UserType.BENEFICIARY.toString())
            .get()
            .addOnSuccessListener { documents ->
                Timber.tag("JosephGwara").d("Create Donation VM Got the data")

                val userList = mutableListOf<User>()
                for (document in documents){
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                    Timber.tag("JosephGwara").d("DocumentID: ${document.id} and ${document.data}")
                }
                val benefitMatcher = userList.filter { users -> users.donationNeeded.toString() == donationType }
                val luckyOne=  benefitMatcher.random()
                _beneficiary.postValue(luckyOne)
            }
            .addOnFailureListener {
                Timber.tag("JosephGwara").d("Error Fetching data CreateDonation VM")
            }
    }

    fun createDonation(donationType: String,recipientName:String?,recipientEmail:String?,message:String ="",timestamp: Long) = viewModelScope.launch (Dispatchers.IO){
        val userEmail = firebaseAuth.currentUser?.email
        if (userEmail != null){
            val donation = Donation(senderEmail = userEmail,recipientName =recipientName, donationType = donationType,recipientEmail = recipientEmail,message = message, timestamp = timestamp)
            if (recipientEmail != null){
                firestore.collection(Constants.FIREBASE_DONATION_COLLECTION)
                    .document(recipientEmail).set(donation)
                    .addOnSuccessListener { documentReference ->
                        Timber.d("$documentReference")
                        _donation.postValue(donation)
                    }
                    .addOnFailureListener { e->
                        Timber.e(e)
                    }
            }

        }
    }

    fun deleteDocument(email:String){
        firestore.collection(Constants.FIREBASE_USER_COLLECTION).document(email)
            .delete()
            .addOnSuccessListener {
                Timber.tag("JosephGwara").d("DocumentDeleted")
            }
            .addOnFailureListener {
                Timber.tag("JosephGwara").d("Failed to delete Document")
            }
    }


}