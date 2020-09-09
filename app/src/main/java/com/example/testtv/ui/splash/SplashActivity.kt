package com.example.testtv.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.example.testtv.ui.LeanbackActivity
import com.example.testtv.utils.Constants

class SplashActivity : LeanbackActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val sharedPref = this?.getSharedPreferences(Constants.APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val completedOnBoarding = sharedPref.getBoolean(Constants.COMPLETED_ONBOARDING,false)
        if (!completedOnBoarding) {
            startActivity(Intent(this,OnBoa))
        }else{

        }
    }
}