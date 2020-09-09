package com.example.testtv.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.leanback.app.BrowseSupportFragment
import com.example.testtv.ui.main.MainActivity
import com.example.testtv.ui.onboard.OnboardingActivity
import com.example.testtv.utils.Constants

class SplashFragment : BrowseSupportFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = context?.getSharedPreferences(
            Constants.APP_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val completedOnBoarding = sharedPref?.getBoolean(Constants.COMPLETED_ONBOARDING, false)
        if (!completedOnBoarding!!) {
            startActivity(Intent(context, OnboardingActivity::class.java))
            activity?.finish()
        }else{
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }

    }
}