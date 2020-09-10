package com.example.testtv.ui.search

import android.os.Bundle
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.ObjectAdapter

class SearchFragment:SearchSupportFragment(),SearchSupportFragment.SearchResultProvider{

    lateinit var arrayObjectAdapter:ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())

    }

    override fun getResultsAdapter(): ObjectAdapter {
        return arrayObjectAdapter
    }

    override fun onQueryTextChange(newQuery: String?): Boolean {
       return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    fun hasResults(): Boolean {
        return true
    }

}