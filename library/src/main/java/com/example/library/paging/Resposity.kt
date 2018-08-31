package com.example.administrator.paging.paging.base

/**
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
interface Resposity<T> {
    fun getDataList(pageSize: Int): Listing<T>
}