package com.example.testtv.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.testtv.R
import com.example.testtv.data.ConvertData
import com.example.testtv.data.models.GoogleData
import com.example.testtv.data.models.Video
import com.example.testtv.ui.presenters.CardPresenter
import com.example.testtv.ui.presenters.IconHeaderItemPresenter
import com.example.testtv.ui.search.SearchActivity
import com.example.testtv.utils.Constants
import java.io.IOException
import java.util.*

class MainFragment : BrowseSupportFragment(){

    lateinit var mCategoryRowAdapter:ArrayObjectAdapter
    lateinit var defaultBackground: Drawable
    lateinit var displayMatrix: DisplayMetrics
    lateinit var backgroundManager: BackgroundManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBackgroundManager()
        setupUIElements()
        setUpEventListner()

    }

    private fun prepareBackgroundManager() {
        backgroundManager = BackgroundManager.getInstance(activity)
        backgroundManager.attach(activity?.window)
        defaultBackground = resources.getDrawable(R.drawable.default_background)
        displayMatrix = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMatrix)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadOptions()
    }

    private fun loadOptions() {
        val jsonFileString = context?.let { getJsonDataFromAsset(it, "data.json") }.toString()
        val googleData: GoogleData =  ConvertData.getData(jsonFileString)
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        for (i in googleData.googlevideos.indices) {
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            for (vedios in googleData.googlevideos.get(i).videos) {
                listRowAdapter.add(vedios)
            }
            val header = HeaderItem(i.toLong(), googleData.googlevideos.get(i).category)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
        adapter = rowsAdapter

        onItemViewClickedListener = ItemViewClickedListener()

    }



    private suspend fun updateBackGround(uri: String){
        val width = displayMatrix.widthPixels
        val height = displayMatrix.heightPixels

        var requestOptions: RequestOptions = RequestOptions().centerCrop().error(defaultBackground)
        Glide.with(context).asBitmap().load(uri)
            .into(object : SimpleTarget<Bitmap>(width, height) {
                override fun onResourceReady(
                    resource: Bitmap?,
                    transition: Transition<in Bitmap>?
                ) {
                    backgroundManager.setBitmap(resource)
                }
            })

     //   8979193490
    }

    private fun setupUIElements(){
        badgeDrawable = activity?.resources?.getDrawable(R.drawable.ic_banner_foreground)
        title = "ROY TV"
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = context?.let { ContextCompat.getColor(it, R.color.panel_background) }!!
        searchAffordanceColor = context?.let { ContextCompat.getColor(it, R.color.search_background) }!!
        /*setHeaderPresenterSelector(object : PresenterSelector() {
            override fun getPresenter(item: Any?): Presenter {
                return IconHeaderItemPresenter()
            }

        })*/
    }
    private fun setUpEventListner() {
        setOnSearchClickedListener(View.OnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        })

        setOnItemViewClickedListener(ItemViewClickedListener())
    }

    class ItemViewClickedListener: OnItemViewClickedListener{
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {

        }

    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}