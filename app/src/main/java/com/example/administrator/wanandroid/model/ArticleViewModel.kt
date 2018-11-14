package com.example.administrator.wanandroid.model

import com.example.administrator.paging.paging.BasePagingViewModel
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.paging.ArticleResposity

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class ArticleViewModel(articleResposity: ArticleResposity) : BasePagingViewModel<ArticleBean>(articleResposity)