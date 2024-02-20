@file:Suppress("unused", "UNUSED_PARAMETER", "UNUSED_VARIABLE")

package com.danamon.core.ext

import android.util.Patterns
import java.util.regex.Pattern


inline fun <T, reified R> ArrayList<T?>?.orNullArrayListNotNot(targetClass: R, transform: (T) -> R): ArrayList<R> {
    return if (this == null) arrayListOf()
    else {
        val arrayListOf = arrayListOf<R>()
        this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
        return arrayListOf
    }
}

inline fun <T, reified R> ArrayList<T>?.orNullArrayListNot(targetClass: R, transform: (T) -> R): ArrayList<R> {
    return if (this == null) arrayListOf()
    else {
        val arrayListOf = arrayListOf<R>()
        this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
        return arrayListOf
    }
}

inline fun <T, reified R> ArrayList<T>.orNullArrayList(targetClass: R, transform: (T) -> R): ArrayList<R> {
    val arrayListOf = arrayListOf<R>()
    this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
    return arrayListOf
}

inline fun <T, reified R> List<T?>?.orNullListNotNot(targetClass: R, transform: (T) -> R): List<R> {
    return if (this == null) listOf()
    else {
        val arrayListOf = arrayListOf<R>()
        this.forEach { arrayListOf.add(it?.let { transform(it) } ?: targetClass) }
        return arrayListOf
    }
}

inline fun <T, reified R> List<T>?.orNullListNot(transform: (T) -> R): List<R> {
    return if (this == null) listOf()
    else {
        val arrayListOf = arrayListOf<R>()
        this.forEach { arrayListOf.add(transform(it)) }
        return arrayListOf
    }
}

inline fun <T, reified R> T?.orNullObject(targetClass: R, transform: (T) -> R): R {
    return if (this == null) targetClass
    else transform(this)
}

fun Int?.orNullBoolean(defaultNull: Boolean = false): Boolean {
    return when (this) {
        1 -> true
        0 -> false
        else -> false
    }
}

fun String?.orNullBoolean(defaultNull: Boolean = false): Boolean {
    return when (this) {
        "1" -> true
        "0" -> false
        else -> defaultNull
    }
}

fun Boolean?.orNull(defaultNull: Boolean = false): Boolean = this ?: defaultNull

fun Float?.orNull(defaultNull: String, functionIfNotNull: (s: String) -> String? = { null }): String {
    return if (this != null) {
        if (functionIfNotNull(this.toString()) != null) functionIfNotNull(this.toString()) ?: defaultNull
        else this.toString()
    } else defaultNull
}

fun Float?.orNull(defaultNull: Float = 0f): Float = this ?: defaultNull

fun Double?.orNull(defaultNull: String, functionIfNotNull: (s: String) -> String? = { null }): String {
    return if (this != null) {
        if (functionIfNotNull(this.toString()) != null) functionIfNotNull(this.toString()) ?: defaultNull
        else this.toString()
    } else defaultNull
}

fun Double?.orNull(defaultNull: Double = 0.0): Double = this ?: defaultNull

fun Int?.orNull(defaultNull: Int = 0): Int = this ?: defaultNull

fun Int?.orNull(defaultNull: String = "0", functionIfNotNull: (i: Int) -> String? = { null }): String {
    return if (this != null) {
        if (functionIfNotNull(this) != null) functionIfNotNull(this) ?: defaultNull
        else this.toString()
    } else defaultNull
}

fun String?.orNull(defaultNull: String = "", functionIfNotNull: (s: String) -> String? = { null }): String {
    return if (this != null) {
        if (functionIfNotNull(this) != null) functionIfNotNull(this) ?: ((if (this.isNumeric(false)) "0" else defaultNull))
        else this
    } else defaultNull
}

fun Int?.isNull(): Boolean = (this == null)

fun Int?.isNullOrZero(): Boolean = (this == null || this == 0)

fun Int?.isNullOrCustom(param: Int = -1): Boolean = (this == null || this == param)

fun Double?.isNullOrZero(): Boolean = (this == null || this == 0.0)

fun Float?.isNullOrZero(): Boolean = (this == null || this == 0f)

fun Long?.isNullOrZero(): Boolean = (this == null || this == 0L)

fun <T> T?.isNull(): Boolean = this == null

fun <T> T?.isNotNull(): Boolean = this != null

fun String.isNumeric(includeSpace: Boolean): Boolean {
    val regex = if (includeSpace)
        "^[0-9 ]+"
    else
        "^[0-9]+"

    return if (this.isNotEmpty())
        Pattern.compile(regex).matcher(this).matches()
    else
        false
}

fun String.isEmailValid(): Boolean {
    return if (this.isEmpty()) false
    else return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}