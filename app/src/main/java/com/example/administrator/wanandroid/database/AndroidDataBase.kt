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

    abstract fun getCollectDao() : CollectedDao

    abstract fun getReadPlanDao() : ReadPlanDao

    abstract fun getStudyProjectDao() : StudyProjectDao

    abstract fun getRecentSearchDao() : RecentSearchDao


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