package com.example.administrator.paging.paging.base

/**
 * Resposity 创建Listing实例，封装所有要观察的属性和状态
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
interface Resposity<T> {
    fun getDataList(pageSize: Int): Listing<T>
}