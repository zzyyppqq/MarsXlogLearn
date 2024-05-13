package com.zyp.xlog

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

class MainActivity : AppCompatActivity() {

    private val xlogSample = XLogSample()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tv = findViewById(R.id.sample_text) as TextView
        tv.setText(xlogSample.stringFromJNI())

        xlogInit()

        findViewById<Button>(R.id.btn_log).setOnClickListener {
            Log.i("ZYP", "test")
        }
    }

    private fun xlogInit() {
        val logPath = Environment.getExternalStorageDirectory().path + "/MarsXlogLearn/xlog"
        val xlog = Xlog()
        Log.setLogImp(xlog)
        Log.setConsoleLogOpen(true)
        Log.appenderOpen(
            Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "LOGSAMPLE", 0
        )
    }
}