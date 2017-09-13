package com.markiiimark.assignment01a_kotlin.model

import com.markiiimark.assignment01a_kotlin.presenter.PalantiriPresenter
import java.util.*

/**
 * Created by MarkiiimarK on 9/10/17.
 */
class PalantiriModel {
    companion object {
        private val TAG = PalantiriModel::class.java.name;
    }

    private var mPalantirManager:PalantiriManager?=null

    fun makePalantiri(palantiriCount:Int) {
        val palantiri = ArrayList<Palantir>(palantiriCount)

        (0.until(palantiriCount - 1)).map { i -> palantiri.add(Palantir(i, Random()))  }

        mPalantirManager = PalantiriManager(palantiri)
    }

    fun acquirePalantir(): Palantir? = mPalantirManager?.acquire()

    fun releasePalantir(palantir: Palantir?) = mPalantirManager?.release(palantir)

}