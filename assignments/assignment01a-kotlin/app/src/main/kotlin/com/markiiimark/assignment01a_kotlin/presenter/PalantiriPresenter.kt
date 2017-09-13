package com.markiiimark.assignment01a_kotlin.presenter

import android.util.Log
import com.markiiimark.assignment01a_kotlin.R
import com.markiiimark.assignment01a_kotlin.model.PalantiriModel
import com.markiiimark.assignment01a_kotlin.utils.Options
import com.markiiimark.assignment01a_kotlin.utils.UiUtils
import com.markiiimark.assignment01a_kotlin.view.DotArrayAdapter.DotColor
import com.markiiimark.assignment01a_kotlin.view.GazingSimulationActivity
import java.lang.ref.WeakReference
import android.content.Intent






/**
 * Created by MarkiiimarK on 9/10/17.
 */
data class PalantiriPresenter(val view:GazingSimulationActivity,
                              var configurationChangeOccured:Boolean=false,
                              var isRunning:Boolean=false,
                              val model:PalantiriModel=PalantiriModel()
                              ) {
    companion object {
        private val TAG = PalantiriPresenter::class.java.name;
    }

    var mBeingThreads = ArrayList<Thread>()

    var mPalantiriColors = ArrayList<DotColor>()
        get() {  return mPalantiriColors  }

    var mBeingsColors = ArrayList<DotColor>()
        get() {  return mBeingsColors  }

    var mView = WeakReference<GazingSimulationActivity>(view)

    init {
        view.intent.let { intent ->
            if (!Options.uniqueInstance.parseArgs(view, makeArgv(intent)))
                UiUtils.showToast(view, R.string.toast_incorrect_arguments);
        }
    }

    fun onConfigurationChange(view:GazingSimulationActivity) {
        Log.d(TAG, "onConfigurationChange() called ")
        mView = WeakReference<GazingSimulationActivity>(view)
        configurationChangeOccured = true
    }

    private fun makeArgv(intent: Intent): Array<String> {
        return arrayOf("-b", // Number of Being threads.
                intent.getStringExtra(GazingSimulationActivity.BEINGS), "-p", // Number of Palantiri.
                intent.getStringExtra(GazingSimulationActivity.PALANTIRI), "-i", // Gazing iterations.
                intent.getStringExtra(GazingSimulationActivity.GAZING_ITERATIONS))
    }

    fun start() {
        model.makePalantiri(Options.uniqueInstance.mNumberOfPalantiri)
        mView.get()?.showBeings()
        mView.get()?.showPalantiri()

    }

    private fun beginBeingThreads(beingCount:Int) {
        (0.until(beingCount - 1)).map {
            val newBeingThread = BeingRunnable(this)
            val threadToAdd = Thread(newBeingThread)
            mBeingThreads.add(threadToAdd)
        }

        mBeingThreads.forEach { t -> t.start() }

    }

    private fun waitForBeingThreads() {
        class WaitThread:Thread() {
            override fun run() {
                var stillRunning = true
                while (stillRunning) {
                    stillRunning = false
                    (0.until(mBeingThreads.size - 1)).map { i ->
                        if (mBeingThreads[i].isAlive) {  stillRunning = true  }
                    }
                }
                mView.get()?.done()
            }
        }
        val mWaitThread = WaitThread()
        mWaitThread.start()

        Thread {
            mBeingThreads.map { t->
                try {  t.join()  } catch (e:InterruptedException) { }
            }


        }
    }

    fun shutdown() {
        synchronized(this) {
            mBeingThreads.forEach {  t -> t.interrupt()  }

            mView.get()?.shutdownOccurred(mBeingThreads.size)
        }
    }
}