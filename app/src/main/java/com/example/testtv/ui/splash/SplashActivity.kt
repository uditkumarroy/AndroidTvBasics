package com.example.testtv.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import com.example.testtv.R
import com.example.testtv.ui.LeanbackActivity
import com.example.testtv.ui.main.MainActivity
import com.example.testtv.ui.onboard.OnboardingActivity
import com.example.testtv.utils.Constants

class SplashActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
    }
}