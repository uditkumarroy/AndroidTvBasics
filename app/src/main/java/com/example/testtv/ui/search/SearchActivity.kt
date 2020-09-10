package com.example.testtv.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.example.testtv.R
import com.example.testtv.ui.LeanbackActivity

class SearchActivity : LeanbackActivity(){

    lateinit var mFragment:SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragment =  supportFragmentManager.findFragmentById(R.id.search_fragment_lay) as SearchFragment

    }

    override fun onSearchRequested(): Boolean {
        if (mFragment.hasResults()){
            startActivity(Intent(this,SearchActivity::class.java))
        }else{
            mFragment.startRecognition()
        }
        return true
    }

}