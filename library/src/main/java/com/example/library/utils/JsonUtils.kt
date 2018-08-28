package com.example.library.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException

import java.lang.reflect.Type

/**
 *
 *
 * Json转换工具类
 */
object JsonUtils {

    private val mGson = Gson()

    /**
     * 将对象准换为json字符串
     *
     * @param object
     * @param <T>
     * @return
    </T> */
    fun <T> serialize(`object`: T): String {
        return mGson.toJson(`object`)
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
    </T> */
    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: String, clz: Class<T>): T {
        return mGson.fromJson(json, clz)
    }

    /**
     * 将json对象转换为实体对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
    </T> */
    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: JsonObject, clz: Class<T>): T {
        return mGson.fromJson(json, clz)
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
    </T> */
    @Throws(JsonSyntaxException::class)
    fun <T> deserialize(json: String, type: Type): T {
        return mGson.fromJson(json, type)
    }
}
