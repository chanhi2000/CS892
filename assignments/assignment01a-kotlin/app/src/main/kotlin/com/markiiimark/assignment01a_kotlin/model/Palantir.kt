package com.markiiimark.assignment01a_kotlin.model

import java.util.*

/**
 * Created by MarkiiimarK on 9/10/17.
 */
data class Palantir(val id:Int, private val random:Random) {

    fun gaze():Boolean {
        try {
            Thread.sleep(  (random.nextInt(4000) + 1000).toLong()  )
            return true
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            return false
        } catch (e: Exception) {
            return false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        return if (other !is Palantir) {  false  } else {  this.id === other.id  }
    }

    override fun hashCode(): Int {
        return id
    }
}