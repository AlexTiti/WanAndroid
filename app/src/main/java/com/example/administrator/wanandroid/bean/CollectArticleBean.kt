package com.example.administrator.wanandroid.bean

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */

data class CollectArticleResponseBody(var curPage: Int = 0,
                               var offset: Int = 0,
                               var isOver: Boolean = false,
                               var pageCount: Int = 0,
                               var size: Int = 0,
                               var total: Int = 0,
                               var datas: List<CollectArticleBean>? = null)





data class CollectArticleBean( var author: String? = null,
                               var chapterId: Int = 0,
                               var chapterName: String? = null,
                               var courseId: Int = 0,
                               var desc: String? = null,
                               var envelopePic: String? = null,
                               var id: Int = 0,
                               var link: String? = null,
                               var niceDate: String? = null,
                               var origin: String? = null,
                               var originId: Int = 0,
                               var publishTime: Long = 0,
                               var title: String? = null,
                               var userId: Int = 0,
                               var visible: Int = 0,
                               var zan: Int = 0)