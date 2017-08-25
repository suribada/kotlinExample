package com.suribada.kotlinbook

import android.annotation.TargetApi
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.os.Build
import kotlinx.android.synthetic.main.kotlin_activity.*

class KotlinActivity : Activity() {

    object AppContext {
        var context : Context? = null

        @TargetApi(Build.VERSION_CODES.M)
        inline fun <reified T> service():T? {
            return context?.getSystemService(T::class.java)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity)
        AppContext.context = this
        val audioManager = AppContext.service<AudioManager>()
    }

}
