package com.zyp.xlog.learn

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Instance = this
        LogHelper.init()
    }

    companion object {
        lateinit var Instance: App
    }

}

val app: App get() = App.Instance