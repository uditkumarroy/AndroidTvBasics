package com.example.testtv.data

import android.util.Log
import com.example.testtv.data.models.GoogleData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object ConvertData {

    fun getData(string:String):GoogleData{
        Log.i("data", string)
        val gson = Gson()
        val data = object : TypeToken<GoogleData>(){}.type
        return gson.fromJson(string,data)
    }
}