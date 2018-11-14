package com.example.administrator.wanandroid.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */

@Entity(tableName = "collect")
  data class CollectArticle ( var articleId : Int ){

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0


}