package com.example.administrator.wanandroid.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.example.administrator.wanandroid.Constants
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.database.ReadPlanArticle
import com.example.administrator.wanandroid.database.StudyProject
import com.example.administrator.wanandroid.http.RetrofitApi
import com.example.administrator.wanandroid.model.ArticleDetailModel
import com.example.administrator.wanandroid.respository.ArticleDetailRepository
import com.example.library.base.BaseCompatActivity
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.toolbar_.*

class ArticleDetailActivity : BaseCompatActivity(), View.OnClickListener {

    private lateinit var mTitle: String
    private lateinit var mUrl: String
    private lateinit var mAuthor: String
    private lateinit var mChapter: String
    private var mChapterId: Int = -1
    private var mId: Int = -1
    private lateinit var agentWeb: AgentWeb

    private val model by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return ArticleDetailModel(ArticleDetailRepository(RetrofitApi.instence,this@ArticleDetailActivity)) as T
            }

        })[ArticleDetailModel::class.java]
    }

    override fun getLayoutId() = R.layout.activity_content

    override fun onErrorViewClick(v: View?) {

    }

    override fun initData() {
        super.initData()
        intent?.run {
            mTitle = getStringExtra(Constants.CONTENT_TITLE)
            mUrl = getStringExtra(Constants.CONTENT_URL)
            mId = getIntExtra(Constants.CONTENT_Id, -1)
            mAuthor = getStringExtra(Constants.CONTENT_AUTHOR)
            mChapter = getStringExtra(Constants.CONTENT_CHAPTER)
            mChapterId = getIntExtra(Constants.CONTENT_CHAPTER_ID,-1)
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        initToolbar()
        initWebView()

    }

    private fun initToolbar() {
        toolbar.let {
            title = mTitle
            setSupportActionBar(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)

        model.contentTitle.observe(this, Observer {
            supportActionBar?.title = it
        })
        model.contentTitle.value = mTitle
    }


    private fun initWebView() {
        model.contentUrl.observe(this, Observer {
            createWebView(it)
        })
        model.contentUrl.value = mUrl
    }

    private fun createWebView(url: String?) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_detail_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.article_action -> {
                createBottomDialog()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_article_collect -> {
                model.collectArticle(mId)
            }
            R.id.btn_article_readLater -> {
                if (mChapterId == 294) {
                    model.projectStudy(StudyProject(mAuthor, mChapter, mUrl, mId, mTitle))
                }else{
                    model.articleReadLater(ReadPlanArticle(mAuthor, mChapter, mUrl, mId, mTitle))
                }
            }
        }

    }

    private fun createBottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_article_detail_action, null)
        val collectButton = view.findViewById<Button>(R.id.btn_article_collect)
        val readPlanButton = view.findViewById<Button>(R.id.btn_article_readLater)

        collectButton.setOnClickListener(this)
        readPlanButton.setOnClickListener(this)

        model.isExistCollected(this,mId)
        model.isAddPlaned(this,mId,mChapterId)


        model.collected.observe(this, Observer {
            if (it!!) collectButton.setText(R.string.cancel_collect_article) else collectButton.setText(R.string.collect_article)
        })

        model.readPlan.observe(this, Observer {
            if (it!!) readPlanButton.setText(R.string.delete_read_plan) else readPlanButton.setText(R.string.add_read_plan)
        })

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    override fun onResume() {
        super.onResume()
        agentWeb.webLifeCycle.onResume()
    }

    override fun onPause() {
        super.onPause()
        agentWeb.webLifeCycle.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        agentWeb.webLifeCycle.onDestroy()
    }

}
