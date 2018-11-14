package com.example.administrator.wanandroid.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author  : Alex
 * @date    : 2018/10/29
 * @version : V 2.0.2
 */
@Entity(tableName = "recent_search")
class RecentSearch(val string: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}