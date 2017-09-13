package com.markiiimark.assignment01a_kotlin.view

import android.os.Bundle
import android.util.Log
import com.markiiimark.assignment01a_kotlin.R
import com.markiiimark.assignment01a_kotlin.utils.UiUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_palantir.*


/**
 * Created by MarkiiimarK on 9/10/17.
 */
class PalantiriActivity : LifecycleLoggingActivity() {
    companion object {
        private val TAG = PalantiriActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palantir)
    }

    fun startSimulation(v: View) {
        val intent = GazingSimulationActivity.makeIntent(
                edt_number_of_beings.text.toString(),
                edt_number_of_palantiri.text.toString(),
                edt_gazing_iterations.text.toString())

        // Verify that the intent will resolve to an Activity.
        if (intent.resolveActivity(packageManager) != null) {
            // Launch Activity.
            startActivity(intent)
            Log.d(TAG, "numBeing: ${edt_number_of_beings.text.toString()}, \n " +
                    "numPalantir: ${edt_number_of_palantiri.text.toString()}, \n " +
                    "numGazingIteration: ${edt_gazing_iterations.text.toString()}")
        } else {
            UiUtils.showToast(this, "Intent did not resolve to an Activity")
        }
    }
}