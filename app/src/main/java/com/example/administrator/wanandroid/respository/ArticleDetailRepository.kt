package com.example.administrator.wanandroid.respository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.administrator.wanandroid.database.AndroidDataBase
import com.example.administrator.wanandroid.database.CollectArticle
import com.example.administrator.wanandroid.database.ReadPlanArticle
import com.example.administrator.wanandroid.database.StudyProject
import com.example.administrator.wanandroid.http.Api
import com.example.administrator.wanandroid.utils.runOnIoThread
import com.example.library.helper.RxHelper
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * @author  : Alex
 * @date    : 2018/10/18
 * @version : V 2.0.0
 */
class ArticleDetailRepository(val api: Api, val context: Context) {


    val articleIsCollected = MutableLiveData<Boolean>()
    val articleIsReadLater = MutableLiveData<Boolean>()


    fun collectArticle(id: Int) {
        api.collectArticle(id)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    if (it.errorCode == 0) {
                        articleIsCollected.value = true
                        addArticleIdToTable(id)
                    }
                }, Consumer {
                    it.message
                })
    }

    fun cancelCollectArticle(id: Int) {
        api.cancelCollectArticle(id)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {
                    if (it.errorCode == 0) {
                        articleIsCollected.value = false
                        deleteArticleIdToTable(id)
                    }
                })
    }


    fun addReadLater(readPlanArticle: ReadPlanArticle) {
        runOnIoThread {
            AndroidDataBase.getInstence(context).getReadPlanDao().insert(readPlanArticle)
            articleIsReadLater.postValue(true)
        }
    }

    fun addStudyProject(readPlanArticle: StudyProject) {
        runOnIoThread {
            AndroidDataBase.getInstence(context).getStudyProjectDao().insert(readPlanArticle)
            articleIsReadLater.postValue(true)
        }
    }

    fun removeReadLater(id: Int) {
        runOnIoThread {
            val readPlanArticle = AndroidDataBase.getInstence(context).getReadPlanDao().getArticle(id)
            AndroidDataBase.getInstence(context).getReadPlanDao().remove(readPlanArticle)
            articleIsReadLater.postValue(false)
        }
    }

    fun removeStudyProject(id: Int) {
        runOnIoThread {
            val readPlanArticle = AndroidDataBase.getInstence(context).getStudyProjectDao().getArticle(id)
            AndroidDataBase.getInstence(context).getStudyProjectDao().remove(readPlanArticle)
            articleIsReadLater.postValue(false)
        }
    }


    fun isColletecd(context: Context, id: Int) {
        runOnIoThread {
            val liva = AndroidDataBase.getInstence(context).getCollectDao().isCollected(id)

            if (liva != null) {
                articleIsCollected.postValue(true)
            } else {
                articleIsCollected.postValue(false)
            }
        }
    }


    fun isRaedPlan(context: Context, id: Int) {
        runOnIoThread {
            val liva = AndroidDataBase.getInstence(context).getReadPlanDao().getArticle(id)
            if (liva != null) {
                articleIsReadLater.postValue(true)
            } else {
                articleIsReadLater.postValue(false)
            }
        }
    }

    fun isStudyProject(context: Context, id: Int) {
        runOnIoThread {
            val liva = AndroidDataBase.getInstence(context).getStudyProjectDao().getArticle(id)
            if (liva != null) {
                articleIsReadLater.postValue(true)
            } else {
                articleIsReadLater.postValue(false)
            }
        }
    }


    fun addArticleIdToTable(id: Int) {
        Observable.create<CollectArticle> {
            it.onNext(CollectArticle(id))

        }.doAfterNext {
            AndroidDataBase.getInstence(context).getCollectDao().insert(it)
        }.compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {

                })
    }

    fun deleteArticleIdToTable(id: Int) {
        Observable.create<CollectArticle> {
            it.onNext(AndroidDataBase.getInstence(context).getCollectDao().isCollected(id))

        }.doAfterNext {

            AndroidDataBase.getInstence(context).getCollectDao().delete(it)
        }.compose(RxHelper.rxSchedulerHelper())
                .subscribe(Consumer {

                })
    }


}