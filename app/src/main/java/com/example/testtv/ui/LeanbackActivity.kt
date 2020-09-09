package com.example.testtv.ui

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.example.testtv.ui.search.SearchActivity

abstract class LeanbackActivity :FragmentActivity(){

    override fun onSearchRequested(): Boolean {
        startActivity(Intent(this,SearchActivity::class.java))
        return true
    }

}