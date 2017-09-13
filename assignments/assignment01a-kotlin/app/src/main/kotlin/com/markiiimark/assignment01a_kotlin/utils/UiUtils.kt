package com.markiiimark.assignment01a_kotlin.utils

import android.animation.AnimatorListenerAdapter
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.animation.Animator
import android.content.Context
import android.widget.Toast
import android.support.annotation.StringRes






/**
 * Created by MarkiiimarK on 9/10/17.
 */
class UiUtils private constructor() {
    init {
        // ensure this class is only used as a utility.
        throw AssertionError()
    }

    companion object {
        private val TAG = UiUtils::class.java.canonicalName
        var sToaster:Toaster? = null

        fun showFab(fab: FloatingActionButton) {
            fab.show()
            fab.animate()
                    .translationY(0f)
                    .setInterpolator(DecelerateInterpolator(2f))
                    .start()
        }

        fun hideFab(fab: FloatingActionButton) {
            fab.hide()
            fab.animate()
                    .translationY(fab.height.toFloat() + 100f)
                    .setInterpolator(AccelerateInterpolator(2f))
                    .start()
        }

        fun revealEditText(text:EditText) {
            var cx:Int = text.right - 30
            var cy:Int = text.bottom - 60

            var finalRadius = Math.max(text.width, text.height).toFloat()
            ViewAnimationUtils.createCircularReveal(text,
                    cx,
                    cy,
                    0f,
                    finalRadius).let { anim ->
                text.visibility = View.VISIBLE
                anim.start()
            }
        }

        fun hideEditText(text: EditText,
                         clear: Boolean) {
            val cx = text.right - 30
            val cy = text.bottom - 60

            val initialRadius = text.width

            ViewAnimationUtils.createCircularReveal(text,
                    cx, cy,
                    initialRadius.toFloat(), 0f).let { anim ->
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        text.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            }

            // Clear the text from the EditText when the user touches the X FAB.
            if (clear)
                text.text.clear()
        }

        fun pauseThread(millisecs:Long) {
            try {  Thread.sleep(millisecs)  }
            catch (e: InterruptedException) {  Thread.currentThread().interrupt() }
            catch (e: Exception) { }
        }

        fun showToast(context: Context, message: String, vararg args: Any) {
            if (sToaster == null) {
                sToaster = ToasterImpl()
            }
            sToaster?.showToast(context,
                    String.format(message, *args),
                    Toast.LENGTH_SHORT)
        }

        fun showToast(context: Context, @StringRes id: Int, vararg args: Any) {
            if (sToaster == null) {
                sToaster = ToasterImpl()
            }
            sToaster?.showToast(context, context.getString(id, *args), Toast.LENGTH_SHORT)
        }

        class ToasterImpl : Toaster {
            override fun showToast(context: Context, message: String, duration: Int) {
                Toast.makeText(context, message, duration).show()
            }
        }
    }

}