package com.example.administrator.wanandroid.http.paging

import com.example.administrator.paging.paging.BasePagingViewModel
import com.example.administrator.wanandroid.bean.ArticleBean

/**
 * @author  : Alex
 * @date    : 2018/08/31
 * @version : V 2.0.0
 */
class ArticleViewModel(articleResposity: ArticleResposity) : BasePagingViewModel<ArticleBean>(articleResposity)