package com.example.administrator.wanandroid.bean

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
data class CategoryResponse(

         var data: List<CategoryBean>? = null,
        var errorCode: Int = 0,
        var errorMsg: String? = null
)



data class CategoryBean(

         var courseId: Int = 0,
        var id: Int = 0,
        var name: String? = null,
        var order: Int = 0,
        var parentChapterId: Int = 0,
        var visible: Int = 0,
        var children: List<CategoryBean>? = null) {

}