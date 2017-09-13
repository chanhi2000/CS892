package com.markiiimark.assignment01a_kotlin.presenter

import android.util.Log
import com.markiiimark.assignment01a_kotlin.model.Palantir
import com.markiiimark.assignment01a_kotlin.utils.Options
import com.markiiimark.assignment01a_kotlin.utils.UiUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by MarkiiimarK on 9/10/17.
 */
class BeingRunnable(presenter:PalantiriPresenter): Runnable {
    companion object {
        private val TAG = BeingRunnable::class.java.name
        private var mGazingThreads = AtomicInteger(0)
        private var mBeingCount = AtomicInteger(0)
    }

    private val mPresenter:PalantiriPresenter=presenter
    var mBeingId:Int = mBeingCount.getAndIncrement()
        get() {  return mBeingId % Options.uniqueInstance.mNumberOfPalantiri  }

    override fun run() {
        UiUtils.pauseThread(500)

//        var i=0
//        for (i in 0.until(Options.uniqueInstance.mGazingIterations-1)) {
//            if (!gazeIntoPalantir(mBeingId))
//                break
//        }
        (0.until(Options.uniqueInstance.mGazingIterations-1)).filter { !gazeIntoPalantir(mBeingId) }.map { i ->
            Log.d(TAG, "Being ${mBeingId} has finished $i of its " +
                    "${Options.uniqueInstance.mGazingIterations} gazing iterations in thread " +
                    "${Thread.currentThread()}")
        }
    }

    private fun gazeIntoPalantir(beingId:Int):Boolean {
        if (!mPresenter.isRunning) {
            Log.d(TAG, "Thread.interrupted() is true for Being ${beingId} in Thread ${Thread.currentThread()}" )
            mPresenter.mView.get()?.threadShutdown(beingId)
            return false
        } else {
            var palantir:Palantir?=null
            try {
                mPresenter.mView.get()?.markWaiting(beingId)
                while (palantir == null) {
                    UiUtils.pauseThread(500)
                    palantir = mPresenter.model.acquirePalantir()
                }

                if (!incrementGazingCountAndCheck(beingId, palantir)) return false

                mPresenter.mView.get()?.markUsed(palantir.id)

                mPresenter.mView.get()?.markGazing(beingId)

                palantir.gaze()

                mPresenter.mView.get()?.markIdle(beingId)
                UiUtils.pauseThread(500)

                mPresenter.mView.get()?.markFree(palantir.id)
                UiUtils.pauseThread(500)

                decrementGazingCount()


            } catch (e:Exception) {
                Log.d(TAG, "Exception caught in Being ${beingId}")

                mPresenter.mView.get()?.threadShutdown(beingId)
                return false
            } finally {
                mPresenter.model.releasePalantir(palantir)
            }
            return true
        }
    }

    private fun incrementGazingCountAndCheck(beingId:Int, palantir:Palantir):Boolean {
        mGazingThreads.incrementAndGet().let { threads ->
            mPresenter.mPalantiriColors.size.let { numPalantiri ->
                if (threads > numPalantiri) {
                    mPresenter.shutdown()
                    return false
                }
                return true
            }
        }
    }

    private fun decrementGazingCount() {
        mGazingThreads.decrementAndGet()
    }
}