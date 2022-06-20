package com.light.cryptocurrency.util

import io.reactivex.Scheduler

interface RxScheduler {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun main(): Scheduler
}