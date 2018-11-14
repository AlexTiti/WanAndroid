package com.example.administrator.wanandroid.ui

import android.os.Bundle
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.ui.fragment.TagArticleFragment
import com.example.library.base.BaseCompatActivity
import kotlinx.android.synthetic.main.toolbar_.*

class TagArticleActivity : BaseCompatActivity() {

    private var tagFragment: TagArticleFragment? = null

    override fun onErrorViewClick(v: View?) {
    }


    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            tagFragment = supportFragmentManager.findFragmentByTag(TagArticleFragment::class.simpleName) as TagArticleFragment?
        } else {
            tagFragment = TagArticleFragment()
        }

        if (!tagFragment!!.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, tagFragment!!, TagArticleFragment::class.simpleName)
                    .commit()
        }

    }

    override fun getLayoutId() = R.layout.activity_tag_article

    fun initTool(articleTagName :String){
        initToolbar(toolbar, articleTagName)
    }

}
