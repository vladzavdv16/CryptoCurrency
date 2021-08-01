package com.light.cryptocurrency.util

import androidx.annotation.NonNull

interface Formatter<T> {

    @NonNull
    fun format(@NonNull value: T) : String
}