@file:Suppress("unused")

package com.danamon.data.implementation.user.local.preference

import android.content.Context
import com.danamon.core.base.BasePreference

class UserPreference(context: Context) : BasePreference(context) {
    fun logout() {
        userId = ""
        userName = ""
        userEmail = ""
        userRole = ""
    }

    /**
     * Preference Dictionary
     */
    var userId: String
        get() = getSharedPrefString(UID)
        set(value) {
            putSharedPrefString(UID, value)
        }

    var userName: String
        get() = getSharedPrefString(NAME)
        set(value) {
            putSharedPrefString(NAME, value)
        }

    var userEmail: String
        get() = getSharedPrefString(EMAIL)
        set(value) {
            putSharedPrefString(EMAIL, value)
        }

    var userRole: String
        get() = getSharedPrefString(ROLE)
        set(value) {
            putSharedPrefString(ROLE, value)
        }

    companion object {
        const val UID = "UID"
        const val NAME = "NAME"
        const val EMAIL = "EMAIL"
        const val ROLE = "ROLE"
    }
}
