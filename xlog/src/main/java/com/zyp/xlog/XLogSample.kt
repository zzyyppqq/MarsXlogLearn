package com.zyp.xlog

class XLogSample {

    /**
     * A native method that is implemented by the 'xlog' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun getPathFromJNI(name: String, logdir: String): String

    companion object {
        // Used to load the 'xlog' library on application startup.
        init {
            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")
            System.loadLibrary("xlog_sample")
        }
    }
}