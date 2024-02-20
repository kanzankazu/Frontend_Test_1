@file:Suppress("unused")

package com.danamon.data.implementation.user.local.preference

import android.content.Context
import com.danamon.core.base.BasePreference

class UserPreference(context: Context) : BasePreference(context) {
    fun signOut() {
        userEmail = ""
        userName = ""
        userId = ""
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

    companion object {
        const val UID: String = ""
        const val NAME: String = ""
        const val EMAIL: String = ""
    }
}
