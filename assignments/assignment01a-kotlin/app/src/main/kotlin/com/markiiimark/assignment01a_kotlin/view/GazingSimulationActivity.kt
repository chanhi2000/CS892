package com.markiiimark.assignment01a_kotlin.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import com.markiiimark.assignment01a_kotlin.R
import com.markiiimark.assignment01a_kotlin.presenter.PalantiriPresenter
import com.markiiimark.assignment01a_kotlin.utils.Options
import com.markiiimark.assignment01a_kotlin.utils.UiUtils
import kotlinx.android.synthetic.main.activity_gazing_simulation.*

/**
 * Created by MarkiiimarK on 9/10/17.
 */
class GazingSimulationActivity : LifecycleLoggingActivity() {
    companion object {
        private const val ACTION_GAZING_SIMULATION = "android.intent.action.GAZING_SIMULATION"

        private const val DEFAULT_BEINGS = "6"
        private const val DEFAULT_PALANTIRI = "4"
        private const val DEFAULT_GAZING_ITERATIONS = "5"

        const val BEINGS = "BEINGS"
        const val PALANTIRI = "PALANTIRI"
        const val GAZING_ITERATIONS = "GAZING_ITERATIONS"

        fun makeIntent(beings:String, palantiri:String, gazingIterations:String): Intent {
            return Intent(ACTION_GAZING_SIMULATION)
                    .putExtra(BEINGS,
                            if(beings.isNotEmpty()) {  beings  } else {  DEFAULT_BEINGS  })
                    .putExtra(PALANTIRI,
                            if(palantiri.isNotEmpty()) {  palantiri  } else {  DEFAULT_PALANTIRI  })
                    .putExtra(GAZING_ITERATIONS,
                            if(gazingIterations.isNotEmpty()) {  gazingIterations  } else {  DEFAULT_GAZING_ITERATIONS  })
        }
    }

    private val mPalantiriAdapter by lazy {
        DotArrayAdapter(this,
                R.layout.palantir_list_element,
                mPalantiriPresenter.mPalantiriColors)
    }
    private val mBeingAdapter by lazy {
        DotArrayAdapter(this,
                R.layout.being_list_element,
                mPalantiriPresenter.mBeingsColors)
    }

    private lateinit var mStartOrStopFab:FloatingActionButton
    private var mPalantiriPresenter = lastNonConfigurationInstance as PalantiriPresenter
        get() {  return mPalantiriPresenter  }
        set(value) {  field = value }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gazing_simulation)

        if (mPalantiriPresenter == null) {
            mPalantiriPresenter = PalantiriPresenter(this)
        } else {
            mPalantiriPresenter.onConfigurationChange(this)
        }

        initializeViews()

        if (mPalantiriPresenter.isRunning) {
            UiUtils.showToast(this, R.string.toast_simulation_resume)
        } else if (mPalantiriPresenter.configurationChangeOccured) {
            runSimulation()
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): PalantiriPresenter {
        return mPalantiriPresenter
    }

    private fun initializeViews() {
        lsv_palantiri.adapter = mPalantiriAdapter
        lsv_beings.adapter = mBeingAdapter
        updateSimulationButtonText()
    }

    private fun runSimulation() {
        mPalantiriPresenter.isRunning = true
        mPalantiriPresenter.start()
        updateSimulationButtonText()
    }

    private fun updateSimulationButtonText() {
        if (mPalantiriPresenter.isRunning) {
            mStartOrStopFab.isEnabled = false
            mPalantiriPresenter.shutdown()
        } else {
            runSimulation()
        }
    }

    fun showBeings() {
        Runnable {
            mBeingAdapter.clear()
            (0.until(Options.uniqueInstance.mNumberOfBeings-1)).map { i ->
                mPalantiriPresenter.mBeingsColors.add(DotArrayAdapter.DotColor.YELLOW)
            }
            mBeingAdapter.notifyDataSetChanged()
        }.let { runnable ->
            runOnUiThread(runnable)
        }
    }

    fun showPalantiri() {
        Runnable {
            mPalantiriAdapter.clear()

            (0.until(Options.uniqueInstance.mNumberOfPalantiri-1)).map { i ->
                mPalantiriPresenter.mPalantiriColors.add(DotArrayAdapter.DotColor.GRAY)
            }
            mPalantiriAdapter.notifyDataSetChanged()
        }.let { runnable ->
            runOnUiThread(runnable)
        }
    }

        private fun markPalantir(index:Int, color:DotArrayAdapter.DotColor) {
            Runnable {
                mPalantiriPresenter.mPalantiriColors.set(index, color)
                mPalantiriAdapter.notifyDataSetChanged()
            }.let { runnable ->
                runOnUiThread(runnable)
            }
        }

        private fun markBeing(index:Int, color:DotArrayAdapter.DotColor) {
            Runnable {
                mPalantiriPresenter.mBeingsColors.set(index, color)
                mPalantiriAdapter.notifyDataSetChanged()
            }.let { runnable ->
                runOnUiThread(runnable)
            }
        }

        fun markFree(index:Int) = markPalantir(index, DotArrayAdapter.DotColor.GREEN)

        fun markUsed(index:Int) = markPalantir(index, DotArrayAdapter.DotColor.RED)

        fun markIdle(index:Int) = markBeing(index, DotArrayAdapter.DotColor.YELLOW)

        fun markGazing(index:Int) = markBeing(index, DotArrayAdapter.DotColor.GREEN)

        fun markWaiting(index:Int) = markBeing(index, DotArrayAdapter.DotColor.RED)

        fun done() {
            Runnable {
                showPalantiri()
                showBeings()

                mPalantiriPresenter.isRunning = false
                updateSimulationButtonText()
                mStartOrStopFab.isEnabled = true

                UiUtils.showToast(this@GazingSimulationActivity, R.string.toast_simulation_complete)
            }.let { runnable ->
                runOnUiThread(runnable)
            }
        }

        fun shutdownOccurred(numberOfSimulationThreads:Int) {
            Runnable {
                UiUtils.showToast(this@GazingSimulationActivity, R.string.toast_simulation_stopped)
            }.let { runnable ->
                runOnUiThread(runnable)
            }
        }

        fun threadShutdown(index:Int) {
            Runnable {
                Log.d("TAG", "Being ${index} was shutdown")

                mPalantiriPresenter.mBeingsColors.set(index, DotArrayAdapter.DotColor.YELLOW)
                mBeingAdapter.notifyDataSetChanged()
                mPalantiriPresenter.isRunning = false
            }.let { runnable ->
                runOnUiThread(runnable)
            }
        }
    }
