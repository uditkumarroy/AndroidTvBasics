package com.example.testtv.ui.presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.example.testtv.R

class IconHeaderItemPresenter : RowHeaderPresenter(){

    var unSelectedIcon:Float = 0.0F

    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {
        unSelectedIcon = parent?.resources?.getFraction(R.fraction.lb_browse_header_unselect_alpha,1,1)!!
        var layoutInflater:LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = layoutInflater.inflate(R.layout.icon_header_item,null)
        view.alpha = unSelectedIcon
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        super.onBindViewHolder(viewHolder, item)

    }


}