package com.example.testtv.ui.playback

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.example.testtv.data.ConvertData
import com.example.testtv.data.models.GoogleData
import com.example.testtv.data.models.Video
import com.example.testtv.utils.Constants
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.IOException
import javax.xml.datatype.DatatypeFactory


class PlaybackFragment : VideoSupportFragment(){

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>
    lateinit var mPlayerAdapter:LeanbackPlayerAdapter
    lateinit var mPlayer: SimpleExoPlayer
    lateinit var mTrackSelector: TrackSelector
    private lateinit var mSelectedVideo: Video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelectedVideo = activity?.intent?.getSerializableExtra(Constants.VIDEO) as Video
        call()
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            //initializePlayer()
        }
    }

    fun call(){
        val glueHost = VideoSupportFragmentGlueHost(this@PlaybackFragment)
        val playerAdapter = MediaPlayerAdapter(context)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.title = mSelectedVideo.title
      //  mTransportControlGlue.subtitle = description
        mTransportControlGlue.playWhenPrepared()

        playerAdapter.setDataSource(Uri.parse(mSelectedVideo.sources[0]))
        //mPlayer = SimpleExoPlayer.Builder(context!!).build()
        mTransportControlGlue.play()
    }


    private fun initializePlayer() {
        val jsonFileString = context?.let { getJsonDataFromAsset(it, "data.json") }.toString()
        val googleData: GoogleData =  ConvertData.getData(jsonFileString)
        mPlayer = SimpleExoPlayer.Builder(context!!).build()
        mPlayerAdapter = LeanbackPlayerAdapter(activity, mPlayer, Constants.UPDATE_DELAY)
       // mVideoPlayerGlue = VideoPlayerGlue(context, mPlayerAdapter, mPlaylistActionListener)
        val playerAdapter = MediaPlayerAdapter(context)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE)
        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = VideoSupportFragmentGlueHost(this@PlaybackFragment)
        mTransportControlGlue.playWhenPrepared()
        play(mSelectedVideo)

    }

    fun play(mSelectedVideo: Video) {
        mTransportControlGlue.title = mSelectedVideo.title
        prepareMediaForPlaying(Uri.parse(mSelectedVideo.sources[0]))
        mTransportControlGlue.play()
    }

    private fun prepareMediaForPlaying(parse: Uri?) {
        val userAgent = Util.getUserAgent(activity!!, "VideoPlayerGlue")
        val mediaSource = ExtractorMediaSource
            .Factory(DefaultDataSourceFactory(context, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(parse)
        mPlayer.prepare(mediaSource)
        mPlayer.playWhenReady = true
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