package com.example.administrator.wanandroid.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */

@Dao
interface CollectedDao {

    @Query("SELECT * FROM collect WHERE articleId = :id")
    fun isCollected(id:Int):CollectArticle

    @Insert
    fun insert(collectArticle: CollectArticle)


    @Delete
    fun delete(collectArticle: CollectArticle)

}