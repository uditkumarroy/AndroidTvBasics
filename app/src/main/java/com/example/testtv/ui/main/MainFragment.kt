package com.example.testtv.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.example.testtv.R

class MainFragment : BrowseSupportFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUi()

    }

    private fun setUi() {
        title = "Roy TV"
        headersState = HEADERS_ENABLED
        brandColor = Color.RED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  loadOptions()
    }




}