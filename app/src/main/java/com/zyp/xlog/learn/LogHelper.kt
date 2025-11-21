package com.zyp.xlog.learn

import android.os.Environment
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog


object LogHelper {

    private var debugLog: Log.LogInstance? = null
    private var traceLog: Log.LogInstance? = null
    private var errorLog: Log.LogInstance? = null

    fun init() {
        xlogInit()
    }

    /**
     * 使用 appenderOpen 初始化全局日志系统时，您需要指定日志级别、写入模式（同步或异步）、缓存目录、日志目录和文件名前缀等参数。
     * 在Android中，初始化后还需调用 Log.setLogImp(new Xlog()) 来将日志实现设置为Xlog。
     *
     * 使用 openLogInstance 创建新实例时，同样可以为其配置独立的参数（如不同的日志级别和文件路径），从而实现日志的分离。
     */
    fun xlogInit() {
        val logPath = Environment.getExternalStorageDirectory().path + "/MarsXlogLearn/xlog"
        val xlog = Xlog()
        Log.setLogImp(xlog)
        Log.setConsoleLogOpen(true)
        // appenderOpen (全局初始化)
        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "LOGSAMPLE", 0)
        // openLogInstance (多实例)
        debugLog = Log.openLogInstance(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "debug", 0)
        traceLog = Log.openLogInstance(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "trace", 0)
        errorLog = Log.openLogInstance(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "error", 0)
    }

    fun log(msg: String) {
        Log.d("app", msg)
    }

    fun debugLog(msg: String) {
        debugLog?.d("debug_log", msg)
    }

    fun traceLog(msg: String) {
        traceLog?.i("debug_log", msg)
    }

    fun errorLog(msg: String) {
        errorLog?.e("debug_log", msg)
    }

    fun close() {
        Log.closeLogInstance("debug")
        Log.closeLogInstance("trace")
        Log.closeLogInstance("error")
    }
}