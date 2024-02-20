package com.danamon.core.ext

fun <T> T.use(listener: T.() -> Unit) = listener.invoke(this)

fun <T, R> T.useReturn(listener: T.() -> R) = listener.invoke(this)