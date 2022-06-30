package com.light.cryptocurrency.util.notifier

import io.reactivex.Completable

interface Notifier {

    fun sendMessage(title: String, message: String, receiver: Class<*>): Completable
}