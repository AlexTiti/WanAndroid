package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.database.ReadPlanArticle
import com.example.administrator.wanandroid.database.StudyProject

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class StudyPeojectDiffUtil : DiffUtil.ItemCallback<StudyProject>() {

    override fun areItemsTheSame(p0: StudyProject, p1: StudyProject): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: StudyProject, p1: StudyProject): Boolean {
        return p0 == p1
    }
}