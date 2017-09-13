package com.markiiimark.assignment01a_kotlin.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by MarkiiimarK on 9/11/17.
 */
abstract class LifecycleLoggingActivity:AppCompatActivity() {
    companion object {
        protected val TAG = LifecycleLoggingActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate(): activity re-created")
        } else {
            Log.d(TAG, "onCreate(): activity created anew")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(): the activity is about to become visible")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(): the activity has become visible (it is now \"resumed\"")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(): another activity is taking focus (this activity is about to be \"paused\"")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(): the acitivty is no longer visible (it is now \"stopped\"")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart(): the activity is about to be restarted")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        Log.d(TAG, "onRetainCustomNonConfigurationInstance()")
        return super.onRetainCustomNonConfigurationInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(): the activity is about to be destroyed")
    }
}