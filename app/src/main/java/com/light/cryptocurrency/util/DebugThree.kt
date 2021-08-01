package com.light.cryptocurrency.util

import timber.log.Timber
import java.util.*


class DebugThree : Timber.DebugTree() {


    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val stackTrace = Throwable().fillInStackTrace().stackTrace
        val ste = stackTrace[5]
        super.log(priority, tag, String.format(Locale.US,
            "[%s] %s(%s:%d): %s",
            Thread.currentThread().name,
            ste.methodName,
            ste.fileName,
            ste.lineNumber,
            message
        ), t)
    }

}