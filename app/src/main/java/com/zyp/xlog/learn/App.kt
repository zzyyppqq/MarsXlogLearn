package com.zyp.xlog.learn

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LogHelper.init()
    }
}