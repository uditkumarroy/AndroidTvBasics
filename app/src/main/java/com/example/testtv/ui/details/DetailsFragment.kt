package com.example.testtv.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.testtv.R
import com.example.testtv.data.models.Video
import com.example.testtv.utils.Constants

class DetailsFragment :DetailsSupportFragment(){

    lateinit var defaultBackground: Drawable
    lateinit var displayMatrix: DisplayMetrics
    lateinit var backgroundManager: BackgroundManager
    private lateinit var mBackgroundManager: BackgroundManager
    private lateinit var mSelectedVideo: Video
    private lateinit var mPresenterSelector:ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBackgroundManager()
        setupData()

    }

    private fun setupData() {
        mSelectedVideo = activity?.intent?.getSerializableExtra(Constants.VIDEO) as Video
        if (mSelectedVideo != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            //setupRelatedMovieListRow()
            adapter = mAdapter
          //  initializeBackground(mSelectedMovie)
          //  onItemViewClickedListener = ItemViewClickedListener()
        } else {
        //    val intent = Intent(activity, TvMainActivity::class.java)
          //  startActivity(intent)
        }
    }

    private fun prepareBackgroundManager() {
        backgroundManager = BackgroundManager.getInstance(activity)
        backgroundManager.attach(activity?.window)
        defaultBackground = resources.getDrawable(R.drawable.default_background)
        displayMatrix = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMatrix)
    }

    private fun setupDetailsOverviewRow() {
        Log.d("TAG", "doInBackground: " + mSelectedVideo?.toString())
        val row = DetailsOverviewRow(mSelectedVideo)
        row.imageDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.default_background) }
        val width = context?.let { convertDpToPixel(it, Constants.DETAIL_THUMB_WIDTH) }
        val height = context?.let { convertDpToPixel(it, Constants.DETAIL_THUMB_HEIGHT) }


        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
            Action(
                Constants.ACTION_WATCH_TRAILER.toLong(),
                "Play"
            )
        )

        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

}