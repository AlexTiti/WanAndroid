package com.example.administrator.wanandroid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */

@Database(entities = [CollectArticle::class,ReadPlanArticle::class,StudyProject::class,RecentSearch::class],version = 1 ,exportSchema = false)
abstract class AndroidDataBase : RoomDatabase() {

    abstract fun getCollectDao() : CollectedDao  // 用于收藏文章操作
    abstract fun getReadPlanDao() : ReadPlanDao  // 用于阅读计划操作
    abstract fun getStudyProjectDao() : StudyProjectDao // 用于项目学习操作
    abstract fun getRecentSearchDao() : RecentSearchDao // 用于最近搜索操作

   companion object {
       @Volatile
      private var instence : AndroidDataBase? = null
           fun getInstence(context: Context) : AndroidDataBase{
               if (instence == null){
                   synchronized(AndroidDataBase::class){
                       if (instence == null){
                           instence = Room.databaseBuilder(context.applicationContext,AndroidDataBase::class.java,"WanAndroid")
                                   .build()
                       }
                   }
               }
               return instence!!
           }
   }
}