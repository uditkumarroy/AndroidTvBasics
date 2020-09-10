package com.example.testtv.ui.details

import android.os.Bundle
import com.example.testtv.R
import com.example.testtv.ui.LeanbackActivity

class DetailsActivity :LeanbackActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
    }
}