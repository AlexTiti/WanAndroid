package com.example.administrator.wanandroid.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
@Entity(tableName = "read_plan")
data class  ReadPlanArticle(var author: String? = null,
                            var chapterName: String? = null,
                            var link: String? = null,
                            var articleId: Int = 0,
                            var title: String? = null
                            ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}