package com.example.administrator.wanandroid.model

import com.example.administrator.paging.paging.BasePagingViewModel
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.paging.TagArticleRepository

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class TagArticleViewModel(articleResposity: TagArticleRepository) : BasePagingViewModel<ArticleBean>(articleResposity)