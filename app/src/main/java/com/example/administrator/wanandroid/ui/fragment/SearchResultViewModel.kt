package com.example.administrator.wanandroid.ui.fragment

import com.example.administrator.paging.paging.BasePagingViewModel
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.http.paging.QueryArticleRepository

class SearchResultViewModel(articleResposity: QueryArticleRepository) : BasePagingViewModel<ArticleBean>(articleResposity) {

}
