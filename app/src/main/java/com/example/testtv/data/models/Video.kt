package com.example.testtv.data.models

import java.io.Serializable

data class Video(
    val background: String,
    val card: String,
    val description: String,
    val sources: List<String>,
    val studio: String,
    val title: String
):Serializable{
    override fun toString(): String {
        return "Video(background='$background', card='$card', description='$description', sources=$sources, studio='$studio', title='$title')"
    }
}