package com.groupfour.khwakhanyawelfare.presentation.onboarding.beneficiary_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.groupfour.khwakhanyawelfare.data.enums.DonationType
import com.groupfour.khwakhanyawelfare.data.enums.UserType
import com.groupfour.khwakhanyawelfare.data.models.User
import com.groupfour.khwakhanyawelfare.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BeneficiaryDetailsViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore): ViewModel() {


    private var _onBoardingComplete = MutableLiveData<Boolean>()
    val onBoardingComplete: LiveData<Boolean> get() = _onBoardingComplete



    fun onOnboardingComplete(name:String,phoneNumber:String,userType: UserType,age:String,school:String,donationType: DonationType) = viewModelScope.launch(Dispatchers.IO) {
        val userEmail = firebaseAuth.currentUser?.email
        if (userEmail != null){
            val user = User(name =name,number = phoneNumber,email = userEmail,userType=userType, age = age, school = school, donationNeeded = donationType, onboardingComplete = true)

            firestore.collection(Constants.FIREBASE_USER_COLLECTION)
                .document(userEmail).set(user)
                .addOnSuccessListener { documentReference ->
                    Timber.d("Beneficiary Details VM successfully created Document doc ref:$documentReference")
                    _onBoardingComplete.postValue(true)
                }
                .addOnFailureListener { e->
                    Timber.d("Failed to create document Beneficiary VM")
                    Timber.e(e)
                }
        }
    }
}