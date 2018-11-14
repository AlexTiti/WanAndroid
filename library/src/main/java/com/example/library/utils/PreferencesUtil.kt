package com.example.library.utils


import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences工具类封装  委托调用
 */
class PreferencesUtil<T>(val key: String, val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        lateinit var preferences: SharedPreferences
        private var mPreferencesName = "share_preference_default"
       public fun get(context: Context) {
            preferences = context.getSharedPreferences(context.packageName + mPreferencesName, Context.MODE_PRIVATE)
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreferences()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = setPreferences(value)

    private fun findPreferences(): T {
        return with(preferences) {
            val res: Any = when (default) {
                is Int -> getInt(key, default)
                is Boolean -> getBoolean(key, default)
                is Float -> getFloat(key, default)
                is String -> getString(key, default)
                is Long -> getLong(key, default)
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }
            res as T
        }
    }

    private fun<T> setPreferences(value: T) = with(preferences.edit()) {
        when (value) {
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }

    /**
     * 设置preferencesName
     *
     * @param preferencesName preferencesName
     */
    private fun setPreferencesName(preferencesName: String) {
        mPreferencesName = preferencesName
    }
}
