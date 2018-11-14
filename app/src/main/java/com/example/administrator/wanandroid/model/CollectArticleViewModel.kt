package com.example.administrator.wanandroid.model

import com.example.administrator.paging.paging.BasePagingViewModel
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.http.paging.CollectArticleResposity

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CollectArticleViewModel(collectArticleResposity: CollectArticleResposity)
    : BasePagingViewModel<CollectArticleBean>(collectArticleResposity)
