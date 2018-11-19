package com.example.administrator.wanandroid.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.administrator.wanandroid.database.ReadPlanArticle
import com.example.administrator.wanandroid.database.StudyProject
import com.example.administrator.wanandroid.respository.ArticleDetailRepository

/**
 * @author  : Alex
 * @date    : 2018/09/05
 * @version : V 2.0.0
 */

public const val ADD_CODE = 0
public const val DELETE_CODE = 1

class ArticleDetailModel(val aricleDetailResposity: ArticleDetailRepository) : ViewModel() {

    val contentTitle = MutableLiveData<String>()
    val contentUrl = MutableLiveData<String>()



    /**
     * 是否收藏
     */
    val collected = Transformations.map(aricleDetailResposity.articleIsCollected) { it }!!

    /**
     * 是否加入阅读计划
     */
    val readPlan = Transformations.map(aricleDetailResposity.articleIsReadLater) { it }!!


    fun collectArticle(id: Int) {

        when (collected.value) {
            false -> {
                aricleDetailResposity.collectArticle(id)
            }
            true -> {
                aricleDetailResposity.cancelCollectArticle(id)
            }
        }
    }

    fun articleReadLater(readPlanArticle: ReadPlanArticle ) {

        when (readPlan.value) {
            false -> {
                aricleDetailResposity.addReadLater(readPlanArticle)
            }
            true -> {
                aricleDetailResposity.removeReadLater(readPlanArticle.articleId)
            }
        }

    }

    fun projectStudy(studyProject: StudyProject ) {

        when (readPlan.value) {
            false -> {
                aricleDetailResposity.addStudyProject(studyProject)
            }
            true -> {
                aricleDetailResposity.removeStudyProject(studyProject.articleId)
            }
        }

    }

    fun initCollectArticle(collected: Boolean) {
        aricleDetailResposity.articleIsCollected.value = collected
    }


    fun initReadLaterArticle(collected: Boolean) {
        aricleDetailResposity.articleIsReadLater.value = collected
    }


    fun isExistCollected(context: Context, id: Int){
        aricleDetailResposity.isColletecd(context,id)

    }

    fun isAddPlaned(context: Context, id: Int,chapterId:Int){
        if (chapterId == 294){
            aricleDetailResposity.isStudyProject(context,id)
        }else{
            aricleDetailResposity.isRaedPlan(context,id)
        }
    }



}