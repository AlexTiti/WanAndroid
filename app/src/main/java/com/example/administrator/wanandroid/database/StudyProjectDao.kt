package com.example.administrator.wanandroid.database

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
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
 interface StudyProjectDao {

    @Insert
    fun insert(readPlanArticle: StudyProject)

    @Delete
    fun remove(readPlanArticle: StudyProject)

    @Query("SELECT * from project")
    fun getArticleList():DataSource.Factory<Int,StudyProject>

    @Query("SELECT * from project WHERE articleId = :id")
    fun getArticle(id :Int):StudyProject

}