package com.markiiimark.assignment01a_kotlin

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.markiiimark.assignment01a_kotlin.view.PalantiriActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by MarkiiimarK on 9/13/17.
 */
@RunWith(AndroidJUnit4::class)
class PalantiriActivityTest {
    companion object {
        private const val TAG = "PalantiriActivityTest"

        private val CONFIG_TIMEOUT = 4000
        private val SHUTDOWN_TIMEOUT = 6000

        private val PALANTIRI = 4
        private val BEINGS = 6
        private  val ITERATIONS = 5

        @Rule
        var activityTestRule = ActivityTestRule<PalantiriActivity>(PalantiriActivity::class.java)

        @Test
        fun palantiriActivityTest() {

        }


    }
}