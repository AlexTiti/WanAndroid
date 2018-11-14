package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.Constants
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.adapter.ReadPlanArticleAdapter
import com.example.administrator.wanandroid.adapter.StudyPeojectDiffUtil
import com.example.administrator.wanandroid.database.StudyProject
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.model.PlanArticleModel
import com.example.administrator.wanandroid.ui.ArticleDetailActivity
import com.example.library.base.fragment.BaseCompatFragment
import kotlinx.android.synthetic.main.article_fragment.*

@Suppress("UNCHECKED_CAST")
/**
 * @author  : Alex
 * @date    : 2018/08/29
 * @version : V 2.0.0
 */
class StudyProjectFragment : BaseCompatFragment(), OnItemClickListener<StudyProject>{

    override fun itemClick(t: StudyProject, position: Int){
        val intent = Intent()
        intent.putExtra(Constants.CONTENT_Id, t.articleId)
        intent.putExtra(Constants.CONTENT_URL, t.link)
        intent.putExtra(Constants.CONTENT_TITLE, t.title)
        intent.putExtra(Constants.CONTENT_AUTHOR, t.author)
        intent.putExtra(Constants.CONTENT_CHAPTER, t.chapterName)
        intent.putExtra(Constants.CONTENT_CHAPTER_ID, 294)
        startActivity(ArticleDetailActivity::class.java, intent)
    }

    override val layoutId = R.layout.plan_read_layout


    private val adapter by lazy {
        ReadPlanArticleAdapter<StudyProject>(StudyPeojectDiffUtil())
    }

    private val model by lazy {
        initModel()
    }

    override fun initUI(view: View, savedInstanceState: Bundle?){
        initSwipe()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        rvArticle.adapter = adapter
        adapter.let { it.itemClickListener = this }
        model.livePagedListProject.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initSwipe(){

    }

    private fun initModel(): PlanArticleModel {
        return ViewModelProviders.of(activity!!)[PlanArticleModel::class.java]
    }

    override fun lazyLoadData(){
       model
    }

    companion object{
        fun newInstance() = StudyProjectFragment()
    }
}