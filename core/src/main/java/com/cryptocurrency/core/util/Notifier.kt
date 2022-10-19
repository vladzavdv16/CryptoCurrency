package com.cryptocurrency.core.util

import io.reactivex.Completable

interface Notifier {

    fun sendMessage(title: String, message: String, receiver: Class<*>): Completable
}