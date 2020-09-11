package com.example.testtv.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.testtv.R
import com.example.testtv.data.ConvertData
import com.example.testtv.data.models.GoogleData
import com.example.testtv.data.models.Video
import com.example.testtv.ui.playback.PlayBackActivity
import com.example.testtv.ui.presenters.CardPresenter
import com.example.testtv.ui.presenters.DetailsDescriptionPresenter
import com.example.testtv.utils.Constants
import com.example.testtv.utils.Constants.ACTION_WATCH_TRAILER
import java.io.IOException
import java.util.*

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
        //setupRelatedMovieListRow()
        //buildUi()

    }

    private fun setupData() {
        mSelectedVideo = activity?.intent?.getSerializableExtra(Constants.VIDEO) as Video
        if (mSelectedVideo != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            setupRelatedMovieListRow()
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
        row.imageDrawable = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_banner_foreground) }
        val width = context?.let { convertDpToPixel(it, Constants.DETAIL_THUMB_WIDTH) }
        val height = context?.let { convertDpToPixel(it, Constants.DETAIL_THUMB_HEIGHT) }
        val actionAdapter = ArrayObjectAdapter()
        if (width != null) {
            if (height != null) {
                Glide.with(context).asBitmap().load(mSelectedVideo.background)
                    .into(object : SimpleTarget<Bitmap>(width, height) {
                        override fun onResourceReady(
                            resource: Bitmap?,
                            transition: Transition<in Bitmap>?
                        ) {
                            backgroundManager.setBitmap(resource)
                        }
                    })
            }
        }
        if (width != null) {
            if (height != null) {
                Glide.with(context).asDrawable().load(mSelectedVideo.card)
                    .into(object : SimpleTarget<Drawable>(width, height){
                        override fun onResourceReady(
                            resource: Drawable?,
                            transition: Transition<in Drawable>?
                        ) {
                            row.imageDrawable = resource
                            mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                        }

                    })
            }
        }


        actionAdapter.add(
            Action(
                Constants.ACTION_WATCH_TRAILER.toLong(),
                "Play"
            )
        )
        row.actionsAdapter = actionAdapter
        mAdapter.add(row)
    }

    private fun setupRelatedMovieListRow() {
        val jsonFileString = context?.let { getJsonDataFromAsset(it, "data.json") }.toString()
        val googleData: GoogleData =  ConvertData.getData(jsonFileString)
        val subcategories = arrayOf(getString(R.string.related_movies))
        val list = googleData.googlevideos[0].videos

        Collections.shuffle(list)
        val listRowAdapter = ArrayObjectAdapter(CardPresenter())
        for (vedios in list) {
            listRowAdapter.add(vedios)
        }

        val header = HeaderItem(0, subcategories[0])
        mAdapter.add(ListRow(header, listRowAdapter))
        mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
    }
    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor =
            context?.let { ContextCompat.getColor(it, R.color.ic_channel_background) }!!

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, Constants.SHARED_ELEMENT_NAME
        )
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH_TRAILER.toLong()) {
                val intent = Intent(activity, PlayBackActivity::class.java)
                intent.putExtra(Constants.VIDEO, mSelectedVideo)
                startActivity(intent)
            } else {
                Toast.makeText(activity, action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
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