package com.markiiimark.assignment01a_kotlin.utils

import android.content.Context

/**
 * Created by MarkiiimarK on 9/10/17.
 */
class Options private constructor() {
    init {  println("This ($this) is a singleton ")  }

    private object Holder {  val Instance = Options()  }      // SINGLETON

    companion object Factory {
        private const val MAX_PALANTIRI = 6
        private const val MAX_BEINGS = 10
        private const val MAX_DURATION = 5000
        private const val MAX_ITERATIONS = 100

        val uniqueInstance:Options by lazy {  Holder.Instance  }   // SINGLETON
    }

    var mNumberOfPalantiri = 0
        get() {  return mNumberOfPalantiri  }

    var mNumberOfBeings = 0
        get() {  return mNumberOfBeings  }

    private var mLeaseDuration = 5000
        get() {  return mLeaseDuration  }

    var mGazingIterations = 10
        get() {  return mGazingIterations  }

    var mDiagnosticsEnabled:Boolean = false
        get() {  return mDiagnosticsEnabled  }

    fun parseArgs(context: Context,
                  argv: Array<String>?): Boolean {
        require(argv == null) {  return false  }

        var argc = 0
        while (argc < argv!!.size) {
            when(argv[argc]) {
                "-b" -> {
                    mNumberOfBeings = Integer.parseInt(argv[argc + 1])
                    if (mNumberOfBeings < 1 || mNumberOfBeings > MAX_BEINGS) {
                        UiUtils.showToast(context, "Please enter a number between 1 and $MAX_BEINGS for # of Beings")
                        return false
                    }
                }
                "-d" -> mDiagnosticsEnabled = argv[argc + 1] == "true"
                "-i" -> {
                    mGazingIterations = Integer.parseInt(argv[argc + 1])
                    if (mGazingIterations < 1 || mGazingIterations > MAX_ITERATIONS) {
                        UiUtils.showToast(context, "Please enter a number between 1 and $MAX_ITERATIONS for the gazing iterations")
                        return false
                    }
                }
                "-l" -> {
                    mLeaseDuration = Integer.parseInt(argv[argc + 1])
                    if (mLeaseDuration < 1000 || mLeaseDuration > MAX_DURATION) {
                        UiUtils.showToast(context, "Please enter a number between 1000 and $MAX_DURATION for the lease duration")
                        return false
                    }
                }
                "-p" -> {
                    mNumberOfPalantiri = Integer.parseInt(argv[argc + 1])
                    if (mNumberOfPalantiri < 1 || mNumberOfPalantiri > MAX_PALANTIRI) {
                        UiUtils.showToast(context, "Please enter a number between 1 and $MAX_PALANTIRI for # of Palantiri")
                        return false
                    }
                }
                else -> return false
            }
            argc+=2
//            if (argv[argc] == "-b") {
//                mNumberOfBeings = Integer.parseInt(argv[argc + 1])
//                if (mNumberOfBeings < 1 || mNumberOfBeings > MAX_BEINGS) {
//                    UiUtils.showToast(context, "Please enter a number between 1 and $MAX_BEINGS for # of Beings")
//                    return false
//                }
//            } else if (argv[argc] == "-d") {
//                mDiagnosticsEnabled = argv[argc + 1] == "true"
//            } else if (argv[argc] == "-i") {
//                mGazingIterations = Integer.parseInt(argv[argc + 1])
//                if (mGazingIterations < 1 || mGazingIterations > MAX_ITERATIONS) {
//                    UiUtils.showToast(context, "Please enter a number between 1 and $MAX_ITERATIONS for the gazing iterations")
//                    return false
//                }
//            } else if (argv[argc] == "-l") {
//                mLeaseDuration = Integer.parseInt(argv[argc + 1])
//                if (mLeaseDuration < 1000 || mLeaseDuration > MAX_DURATION) {
//                    UiUtils.showToast(context, "Please enter a number between 1000 and $MAX_DURATION for the lease duration")
//                    return false
//                }
//            } else if (argv[argc] == "-p") {
//                mNumberOfPalantiri = Integer.parseInt(argv[argc + 1])
//                if (mNumberOfPalantiri < 1 || mNumberOfPalantiri > MAX_PALANTIRI) {
//                    UiUtils.showToast(context, "Please enter a number between 1 and $MAX_PALANTIRI for # of Palantiri")
//                    return false
//                }
//            } else {
//                return false
//            }
//            argc += 2
        }
        return true
    }
}