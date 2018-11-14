package com.example.administrator.wanandroid.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.UserDialogFragment
import com.example.administrator.wanandroid.ui.fragment.ProjectFragment
import com.example.administrator.wanandroid.ui.fragment.NavigationFragment
import com.example.administrator.wanandroid.ui.fragment.PageFragment
import com.example.administrator.wanandroid.ui.fragment.TagWeiXinFragment
import com.example.library.base.BaseCompatActivity
import com.example.library.utils.PreferencesUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.toolbar_.*

const val bottomIndex = "Bottom_Index"

class MainActivity : BaseCompatActivity() {

    lateinit var pageFragment: PageFragment
    lateinit var knowledgeFragment: ProjectFragment
    lateinit var navigationFragment: NavigationFragment
    lateinit var projectFragment: TagWeiXinFragment
    var currentIndex = 0

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {

            R.id.page -> {
                showFragmentIndex(0)
                true
            }
            R.id.knowledge -> {
                showFragmentIndex(1)
                true
            }
            R.id.navigation -> {
                showFragmentIndex(2)
                true
            }
            R.id.project -> {
                showFragmentIndex(3)
                true
            }
            else -> {
                false
            }

        }

    }

    private val drawNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {

        Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
        when (it.itemId) {

            R.id.logout -> {
                loginOut()
            }
            else -> {

            }
        }
        layout.closeDrawer(Gravity.START)
        true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.search -> {
                startActivity(SearchActivity::class.java)
            }
        }
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        layout.run {
            val toggle = ActionBarDrawerToggle(this@MainActivity, this, toolbar, R.string.navigation_open, R.string.navigation_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigationView.setNavigationItemSelectedListener(drawNavigationItemSelectedListener)
        val imageView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.ivHeadView)
        val textView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.tvUserName)
        var userName: String by PreferencesUtil<String>("userName", "Android")
        textView.text = userName
        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            pageFragment = fragmentManager.getFragment(savedInstanceState, PageFragment::class.java.simpleName) as PageFragment
            knowledgeFragment = fragmentManager.getFragment(savedInstanceState, ProjectFragment::class.java.simpleName) as ProjectFragment
            navigationFragment = fragmentManager.getFragment(savedInstanceState, NavigationFragment::class.java.simpleName) as NavigationFragment
            projectFragment = fragmentManager.getFragment(savedInstanceState, TagWeiXinFragment::class.java.simpleName) as TagWeiXinFragment
            currentIndex = savedInstanceState.getInt(bottomIndex)
        } else {
            pageFragment = PageFragment.newInstance()
            knowledgeFragment = ProjectFragment.newInstance()
            navigationFragment = NavigationFragment.newInstance()
            projectFragment = TagWeiXinFragment.newInstance()
        }

        if (!pageFragment.isAdded) {
            with(fragmentManager) {
                beginTransaction().add(R.id.frameLayout, pageFragment, PageFragment::class.java.simpleName)
                        .commit()
            }
        }

        if (!knowledgeFragment.isAdded) {
            with(fragmentManager) {
                beginTransaction().add(R.id.frameLayout, knowledgeFragment, ProjectFragment::class.java.simpleName)
                        .commit()
            }
        }
        if (!navigationFragment.isAdded) {
            with(fragmentManager) {
                beginTransaction().add(R.id.frameLayout, navigationFragment, NavigationFragment::class.java.simpleName)
                        .commit()
            }
        }
        if (!projectFragment.isAdded) {
            with(fragmentManager) {
                beginTransaction().add(R.id.frameLayout, projectFragment, TagWeiXinFragment::class.java.simpleName)
                        .commit()
            }
        }
        showFragmentIndex(currentIndex)
    }

    private fun loginOut() {
        val listener = object : UserDialogFragment.DialogButtonClickListener {
            override fun positiveButtonClick(dialog: DialogInterface?) {
                var userLogin: Boolean by PreferencesUtil("login", false)
                userLogin = false
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }

            override fun negativeButtonClick(dialog: DialogInterface?) {

            }
        }

        val dialogFragment = UserDialogFragment.Builder()
                .setMessage("确定退出登陆吗？")
                .setButtonListener(listener)
                .build()
        dialogFragment.show(supportFragmentManager, MainActivity::class.java.name)
    }

    private fun showFragmentIndex(index: Int) {
        val transaction = fragmentManager.beginTransaction()

        currentIndex = index
        when (index) {
            0 -> {
                toolbar.title = "首页"
                transaction.show(pageFragment)
                        .hide(knowledgeFragment)
                        .hide(navigationFragment)
                        .hide(projectFragment)
            }
            1 -> {
                toolbar.title = "知识"

                transaction.show(knowledgeFragment)
                        .hide(pageFragment)
                        .hide(navigationFragment)
                        .hide(projectFragment)
            }
            2 -> {
                toolbar.title = "导航"
                transaction.show(navigationFragment)
                        .hide(pageFragment)
                        .hide(knowledgeFragment)
                        .hide(projectFragment)

            }
            3 -> {
                toolbar.title = "项目"
                transaction.show(projectFragment)
                        .hide(pageFragment)
                        .hide(knowledgeFragment)
                        .hide(navigationFragment)

            }
            else -> {

            }
        }
        transaction.commit()

    }

    override fun getLayoutId() = R.layout.activity_main

    override fun onErrorViewClick(v: View?) {

    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (pageFragment.isAdded) {
            fragmentManager.putFragment(outState!!, PageFragment::class.java.simpleName, pageFragment)
        }
        if (knowledgeFragment.isAdded) {
            fragmentManager.putFragment(outState!!, ProjectFragment::class.java.simpleName, knowledgeFragment)
        }
        if (navigationFragment.isAdded) {
            fragmentManager.putFragment(outState!!, NavigationFragment::class.java.simpleName, navigationFragment)
        }
        if (projectFragment.isAdded) {
            fragmentManager.putFragment(outState!!, TagWeiXinFragment::class.java.simpleName, projectFragment)
        }

        outState?.putInt(bottomIndex, currentIndex)
    }

}
