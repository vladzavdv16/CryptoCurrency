package com.light.cryptocurrency.util.formatter

interface Formatter<T> {

    fun format(value: T) : String
}