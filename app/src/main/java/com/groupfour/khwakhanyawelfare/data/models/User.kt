package com.groupfour.khwakhanyawelfare.data.models

import com.groupfour.khwakhanyawelfare.data.enums.DonationType
import com.groupfour.khwakhanyawelfare.data.enums.UserType

data class User(val email:String? = null,
                val name:String? = null,
                val number:String? = null,
                val userType: UserType? = null,
                val onboardingComplete:Boolean? = null,
                val age:String? = null,
                val school:String? = null,
                val donationNeeded:DonationType? = null)

