package com.zyp.xlog.learn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tencent.mars.xlog.Log
import com.zyp.xlog.learn.R
import com.zyp.xlog.XLogSample


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tv = findViewById<TextView>(R.id.sample_text)



        findViewById<Button>(R.id.btn_test).setOnClickListener {
            tv.text = LogHelper.xlogSample.stringFromJNI()
        }


        findViewById<Button>(R.id.btn_log).setOnClickListener {
            for (i in 0..1000) {
                Log.i("ZYP", "mar xlog test: $i")
            }
        }

        findViewById<Button>(R.id.btn_debug_log).setOnClickListener {
            LogHelper.debugLog("I am debug log")
        }

        findViewById<Button>(R.id.btn_trace_log).setOnClickListener {
            LogHelper.debugLog("I am trace log")
        }

        findViewById<Button>(R.id.btn_error_log).setOnClickListener {
            try {
                1 / 0
            } catch (e: Throwable) {
                LogHelper.errorLog(e)
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        LogHelper.close()
    }

}