package com.markiiimark.assignment01a_kotlin.utils

import android.content.Context

/**
 * Created by MarkiiimarK on 9/10/17.
 */
interface Toaster {
    fun showToast(context: Context, message:String, duration:Int)
}