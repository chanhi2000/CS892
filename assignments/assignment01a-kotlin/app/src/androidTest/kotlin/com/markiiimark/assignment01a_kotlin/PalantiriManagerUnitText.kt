package com.markiiimark.assignment01a_kotlin

import com.markiiimark.assignment01a_kotlin.model.Palantir
import com.markiiimark.assignment01a_kotlin.model.PalantiriManager
import junit.framework.Assert
import org.junit.Test
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by MarkiiimarK on 9/13/17.
 */
class PalantiriManagerUnitText {
    @Volatile var mFailed = false
    @Volatile var mInterrupted = false
    @Volatile var exc = false

    @Test fun testPalantiriManager() {
        val palantiriManager = makePalantiri(2)
        assert(palantiriManager != null)
    }

    fun makePalantiri(palantiriCount:Int):PalantiriManager {
        val palantiri = mutableListOf<Palantir>()

        val random = Random()

        0.until(palantiriCount-1).map {  i -> palantiri.add(Palantir(i, random))  }
        return PalantiriManager(palantiri)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testAcquire() {
        Thread {
            try {
                val palantiriManager = makePalantiri(2)
                assert(palantiriManager.availablePermits() == 2)

                palantiriManager.acquire()
                assert(palantiriManager.availablePermits() == 1)

                palantiriManager.acquire()
                assert(palantiriManager.availablePermits() == 0)

            } catch (e: AssertionError) {
                exc = true
                println(e)
            }
        }.let { t ->
            t.start()
            t.join()
            assert(exc == false)
            exc = false
        }

    }

    @Test
    @Throws(InterruptedException::class)
    fun testRelease() {
        Thread {
            try {
                val palantiriManager = makePalantiri(2)
                assert(palantiriManager.availablePermits() == 2)

                val palantir1 = palantiriManager.acquire()
                assert(palantiriManager.availablePermits() == 1)

                val palantir2 = palantiriManager.acquire()
                assert(palantiriManager.availablePermits() == 0)

                palantiriManager.release(palantir1)
                assert(palantiriManager.availablePermits() == 1)

                palantiriManager.release(palantir2)
                assert(palantiriManager.availablePermits() == 2)

                palantiriManager.release(null)
                assert(palantiriManager.availablePermits() == 2)
            } catch (e:AssertionError) {
                exc = true
            }
        }.let { t ->
            t.start()
            t.join()
            assert(exc == false)
            exc = true
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun testAvailablePalantiri() {
        Thread {
            try {
                val palantiriManager = makePalantiri(2)
                assert(palantiriManager.availablePermits() == 2)
                palantiriManager.acquire()
                assert(palantiriManager.availablePermits() == 1)
            } catch (e:AssertionError) {
                exc = true
            }
        }.let { t ->
            t.start()
            t.join()
            assert(exc == false)
            exc = true
        }
    }

    @Test
    fun testConcurrentAccess() {
        val THREAD_COUNT = 6
        val PERMIT_COUNT = 2
        val ACCESS_COUNT = 10

        val palantiriManager = makePalantiri(PERMIT_COUNT)
        assert(THREAD_COUNT > PERMIT_COUNT)

        val runningThreads = AtomicLong(0)

        val threads = arrayOf<Thread>()

         for (i in 0.until(THREAD_COUNT-1)) {
            Thread {
                val random = Random()
                loop@ for (j in 0.until(ACCESS_COUNT-1)) {
                    var palantir:Palantir?=null
                    try {
                        // Acquire a permit from the Manager.
                        while (true) {
                            palantir = palantiriManager.acquire()
                            if (palantir == null) {
                                Thread.sleep(100)
                                break
                            }
                        }
                    } catch (e: Exception) {
                        mInterrupted = true
                        return@loop
                    }

                    runningThreads.incrementAndGet().let { running:Long ->
                        if (running > PERMIT_COUNT) throw RuntimeException()
                    }

                    try {
                        Thread.sleep(random.nextInt(140) + 10L)
                    } catch (e:InterruptedException) {}

                    runningThreads.decrementAndGet()
                    palantiriManager.release(palantir)
                }
            }.let { thread ->
                thread.setUncaughtExceptionHandler { t, e ->
                    System.out.println("uncaughtException in testConcurrentAccess() + ${e.printStackTrace()}")
                    mFailed = true
                }
                threads[i] = thread
            }
        }

        threads.forEach {  t -> t.start()  }
        threads.forEach {  t ->
            try {
                t.join()
            } catch (e:InterruptedException) {
                Assert.fail("The main thread was interrupted for some reason.")
            }
        }
        assert(!mFailed)
        assert(!mInterrupted, {
            "One of the threads was interrupted while calling acquire(). " + "This shouldn't happen (even if your Semaphore is wrong)."
        })

    }
}