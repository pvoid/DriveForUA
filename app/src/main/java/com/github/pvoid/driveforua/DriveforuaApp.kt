package com.github.pvoid.driveforua

import com.facebook.FacebookSdk
import android.app.Application
import com.facebook.appevents.AppEventsLogger

class DriveforuaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}