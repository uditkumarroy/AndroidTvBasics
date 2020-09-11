package com.example.testtv.ui.playback

import android.os.Bundle
import android.view.KeyEvent
import com.example.testtv.ui.LeanbackActivity

class PlayBackActivity :LeanbackActivity(){

    private val gamepadTriggerPressed = false
    private lateinit var mPlaybackFragment:PlaybackFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPlaybackFragment =PlaybackFragment()
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PlaybackFragment())
            .commit()

    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {



        return super.onKeyDown(keyCode, event)

    }
}