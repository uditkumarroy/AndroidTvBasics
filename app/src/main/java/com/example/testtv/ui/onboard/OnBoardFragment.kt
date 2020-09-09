package com.example.testtv.ui.onboard

import android.animation.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.app.OnboardingSupportFragment
import com.example.testtv.R
import com.example.testtv.ui.main.MainActivity
import com.example.testtv.utils.Constants

class OnBoardFragment : OnboardingSupportFragment(){

    val pageTitles = intArrayOf(
        R.string.onboarding_title_welcome,
        R.string.onboarding_title_design,
        R.string.onboarding_title_simple,
        R.string.onboarding_title_project
    )

    val pageDescriptions = intArrayOf(
        R.string.onboarding_description_welcome,
        R.string.onboarding_description_design,
        R.string.onboarding_description_simple,
        R.string.onboarding_description_project
    )

    private lateinit var contentView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logoResourceId = R.drawable.ic_banner_foreground
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onFinishFragment() {
        super.onFinishFragment()
        val sharedPref = activity?.getSharedPreferences(
            Constants.APP_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        with(sharedPref?.edit()){
            this?.putBoolean(Constants.APP_SHARED_PREFERENCE, true)
        }
        activity?.finish()
        startActivity(Intent(context,MainActivity::class.java))
    }

    override fun getPageCount(): Int {
       return pageTitles.size
    }

    override fun getPageTitle(pageIndex: Int): CharSequence {
        return getString(pageTitles[pageIndex])
    }

    override fun getPageDescription(pageIndex: Int): CharSequence {
        return getString(pageDescriptions[pageIndex])
    }

    override fun onCreateBackgroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        val bgView = View(activity)
        bgView.setBackgroundColor(resources.getColor(R.color.ic_channel_background))
        return bgView
    }

    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setImageResource(R.drawable.ic_banner_foreground)
            setPadding(0, 32, 0, 32)
            contentView = this
        }
    }

    override fun onCreateForegroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return null
    }

    public override fun onCreateLogoAnimation(): Animator =
        AnimatorInflater.loadAnimator(context, R.animator.lb_guidedstep_slide_down)

    override fun onCreateEnterAnimation(): Animator =
        ObjectAnimator.ofFloat(contentView, View.SCALE_X, 0.2f, 1.0f)
            .setDuration(16)

    override fun onPageChanged(newPage: Int, previousPage: Int) {
        // Create a fade-out animation used to fade out previousPage and, once
        // done, swaps the contentView image with the next page's image.
        val fadeOut = ObjectAnimator.ofFloat(contentView, View.ALPHA, 1.0f, 0.0f)
            .setDuration(16)
            .apply {
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        contentView.setImageResource(R.drawable.ic_launcher_background)
                    }
                })
            }
        // Create a fade-in animation used to fade in nextPage
        val fadeIn = ObjectAnimator.ofFloat(contentView, View.ALPHA, 0.0f, 1.0f)
            .setDuration(16)
        // Create AnimatorSet with our fade-out and fade-in animators, and start it
        AnimatorSet().apply {
            playSequentially(fadeOut, fadeIn)
            start()
        }
    }

    override fun onProvideTheme(): Int = R.style.Theme_Leanback_Onboarding


}