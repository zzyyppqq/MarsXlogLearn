package com.zyp.xlog

class XLogSample {

    /**
     * A native method that is implemented by the 'xlog' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'xlog' library on application startup.
        init {
            System.loadLibrary("xlog_sample")
        }
    }
}