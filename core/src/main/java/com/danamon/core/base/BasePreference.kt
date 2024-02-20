@file:Suppress("MemberVisibilityCanBePrivate")

package com.danamon.core.base

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.danamon.core.ext.decodedString
import com.danamon.core.ext.encodeString
import com.danamon.core.ext.toBooleanOrFalse
import com.danamon.core.ext.toIntOrDefault
import com.google.gson.Gson

/**
IMPLEMENT in SubClass

companion object {
const val PREFS_NAME = "SHARED_PREF"
private var userPreference: UserPreference? = null

private fun newInstance(context: Context): UserPreference {
if (userPreference == null) {
userPreference = UserPreference(context.applicationContext)
}
return userPreference!!
}

@JvmStatic
val instance: UserPreference
get() = userPreference ?: newInstance(MyApplication.getInstance())
}

var login: Boolean
get() = getSharedPrefBoolean(Const.SharedPreference.LOGIN)
set(b) = putSharedPrefBoolean(Const.SharedPreference.LOGIN, b)

IMPLEMENT in ApplicationClass

companion object {
lateinit var instance: MyApplication

fun getApp(): MyApplication {
return instance
}
}

UserPreference = ClassName
MyApplication = ApplicationClass
 */
abstract class BasePreference(context: Context) {
    private val prefsName = "prefsName"

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    val key: ByteArray = "default_key".toByteArray(Charsets.UTF_8)

    /**
     * Preference Helper
     */
    fun removeSharedPrefByKey(key: String) = sharedPreferences.edit().remove(key).commit()

    fun isContainKey(key: String): Boolean = sharedPreferences.contains(key)

    /**
     * Preference Editor
     */
    fun putSharedPrefString(key: String, value: String) = sharedPreferences.edit().putString(key, value.encodeString()).commit()

    fun getSharedPrefString(key: String): String = (sharedPreferences.getString(key, "") ?: "").decodedString()

    fun putSharedPrefInt(key: String, value: Int) = putSharedPrefString(key, value.toString())//sharedPreferences.edit().putInt(key, value).commit()

    fun getSharedPrefInt(key: String, defaultValue: Int = 0): Int = getSharedPrefString(key).toIntOrDefault(defaultValue)//sharedPreferences.getInt(key, 0)

    fun putSharedPrefLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).commit()

    fun getSharedPrefLong(key: String): Long = sharedPreferences.getLong(key, 0)

    fun putSharedPrefBoolean(key: String, value: Boolean) = putSharedPrefString(key, value.toString())//sharedPreferences.edit().putBoolean(key, value).commit()

    fun getSharedPrefBoolean(key: String, defaultValue: Boolean = false): Boolean = getSharedPrefString(key).toBooleanOrFalse(defaultValue)//sharedPreferences.getBoolean(key, false)

    fun putSharedPrefFloat(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).commit()

    fun getSharedPrefFloat(key: String): Float = sharedPreferences.getFloat(key, 0f)

    fun putSharedPrefStringArray(key: String, values: ArrayList<String>) {
        for (i in values.indices) {
            putSharedPrefString(key + "_" + i, values[i])
        }
        putSharedPrefInt(key + "_size", values.size)
    }

    fun getSharedPrefStringArray(key: String): ArrayList<String> {
        val values = ArrayList<String>()
        for (i in 0 until getSharedPrefInt(key + "_size")) {
            values.add(getSharedPrefString(key + "_" + i))
        }
        return values
    }

    fun putListString(key: String?, stringList: ArrayList<String>) {
        val myStringList = stringList.toTypedArray()
        sharedPreferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).commit()
    }

    fun getListString(key: String?): ArrayList<String> {
        return ArrayList(listOf(*TextUtils.split(sharedPreferences.getString(key, ""), "‚‗‚")))
    }

    fun putSharedPrefObject(key: String, obj: Any) {
        val gson = Gson()
        putSharedPrefString(key, gson.toJson(obj))
    }

    inline fun <reified T> getSharedPrefObject(key: String): T? {
        val json = getSharedPrefString(key)
        return Gson().fromJson(json, T::class.java)
    }

    fun putSharedPrefObjectList(key: String, objArray: ArrayList<Any>?) {
        val gson = Gson()
        val objStrings = ArrayList<String>()
        if (objArray != null) {
            for (obj in objArray) {
                objStrings.add(gson.toJson(obj))
            }
            putListString(key, objStrings)
        }
    }

    fun getSharedPrefObjectList(key: String, mClass: Class<*>): ArrayList<Any> {
        val gson = Gson()
        val objStrings = getListString(key)
        val objects = ArrayList<Any>()
        for (jObjString in objStrings) {
            val value = gson.fromJson(jObjString, mClass)
            objects.add(value)
        }
        return objects
    }

    /*private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(ByteArray(16)))
        val encryptedValue = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    private fun decrypt(encryptedValue: String): String {
        val encryptedValueByteArray = Base64.decode(encryptedValue, Base64.DEFAULT)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(ByteArray(16)))
        val decryptedByteArray = cipher.doFinal(encryptedValueByteArray)
        return String(decryptedByteArray, Charsets.UTF_8)
    }*/
}
