package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.bean.CollectArticleBean

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class CollectArticleDiffUtil : DiffUtil.ItemCallback<CollectArticleBean>() {

    override fun areItemsTheSame(p0: CollectArticleBean, p1: CollectArticleBean): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: CollectArticleBean, p1: CollectArticleBean): Boolean {
        return p0 == p1
    }
}