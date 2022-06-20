package com.light.cryptocurrency.util


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxSchedulerImpl @Inject constructor(
    private val executor: ExecutorService
): RxScheduler {

    override fun io() = Schedulers.from(executor)

    override fun computation() = Schedulers.computation()

    override fun main() = AndroidSchedulers.mainThread()
}