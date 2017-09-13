package com.markiiimark.assignment01a_kotlin.model

import com.markiiimark.assignment01a_kotlin.model.PalantiriManager.PalantirEntry
import com.markiiimark.assignment01a_kotlin.utils.UiUtils


/**
 * Created by MarkiiimarK on 9/10/17.
 */
class PalantiriManager {
    companion object {
        protected val TAG = PalantiriManager::class.java.name
    }

    inner class PalantirEntry(palantir:Palantir, available:Boolean) {
        var mPalantir:Palantir=palantir
        var mAvailable:Boolean=available
    }

    @Volatile var mLocked:Boolean?=null
    protected var mPalantiriList = mutableListOf<PalantirEntry>()

    constructor(palantiri: List<Palantir>) {
        palantiri.forEach { palantir ->
            mPalantiriList.add(PalantirEntry(palantir, true))
        }
    }

    fun acquire():Palantir? {
        var palantir:Palantir?=null

        while (mLocked == true) UiUtils.pauseThread(100);
        mLocked = true

        // Find an available palantiri if one exists.
        for (pe in mPalantiriList) {
            // If the palantir is available mark it as being not
            // available and return the palantir.
            if (pe.mAvailable == true) {
                pe.mAvailable = false
                palantir = pe.mPalantir
                break
            }
        }

        mLocked = false
        return palantir
    }

    fun release(palantir:Palantir?) {
        require(palantir != null) { return }

        while (mLocked == true) UiUtils.pauseThread(100)
        mLocked = true

        // Iterate through all the entries in the list.
        for (pe in mPalantiriList) {
            // Make the palantir available (i.e., set it to
            // "true") when the palantir matches.
            if (pe.mPalantir == palantir) {
                pe.mAvailable = true
                break
            }
        }

        mLocked = false
    }

    fun availablePermits():Int {
        var availablePalantiri:Int = 0

        while (mLocked == true) UiUtils.pauseThread(100)
        mLocked = true

        mPalantiriList.filter { it.mAvailable == true }.map { ++availablePalantiri }

        mLocked = false

        return availablePalantiri
    }


}