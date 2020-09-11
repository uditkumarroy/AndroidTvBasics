package com.example.testtv.ui.presenters

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.testtv.data.models.Video

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: AbstractDetailsDescriptionPresenter.ViewHolder,
        item: Any
    ) {
        val video = item as Video

        viewHolder.title.text = video.title
        viewHolder.subtitle.text = video.studio
        viewHolder.body.text = video.description
    }
}