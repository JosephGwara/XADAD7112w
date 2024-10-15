package com.groupfour.khwakhanyawelfare.data.models

import com.groupfour.khwakhanyawelfare.data.enums.UserType

data class User(val email:String,val userType: UserType,val onboardingComplete:Boolean = false)

