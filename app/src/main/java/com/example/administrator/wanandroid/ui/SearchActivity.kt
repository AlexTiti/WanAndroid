package com.example.administrator.wanandroid.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.database.AndroidDataBase
import com.example.administrator.wanandroid.database.RecentSearch
import com.example.administrator.wanandroid.ui.fragment.SearchResultFragment
import com.example.administrator.wanandroid.ui.fragment.SearchTagFragment
import com.example.administrator.wanandroid.utils.runOnIoThread
import com.example.library.base.BaseCompatActivity
import kotlinx.android.synthetic.main.toolbar_.*

const val EXTRAL_SEARCH = "extral_text"

class SearchActivity : BaseCompatActivity() {
    lateinit var searchTagFragment: SearchTagFragment
    lateinit var searResultFragment: SearchResultFragment
    override fun onErrorViewClick(v: View?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.title = "Search"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            searchTagFragment = SearchTagFragment.newInstance()
            searResultFragment = SearchResultFragment.newInstance(null)
        } else {
            searchTagFragment = supportFragmentManager.getFragment(savedInstanceState, SearchTagFragment::class.simpleName!!) as SearchTagFragment
            searResultFragment = supportFragmentManager.getFragment(savedInstanceState, SearchResultFragment::class.simpleName!!) as SearchResultFragment
        }

        if (!searchTagFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, searchTagFragment)
                    .commit()
        }

        if (searResultFragment != null && !searResultFragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, searResultFragment)
                    .commit()
        }

        showFragment(0)
    }

    override fun getLayoutId() = R.layout.activity_search

    lateinit var searchView: SearchView
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.searchView)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = false
        searchView.isQueryRefinementEnabled = false
        searchView.requestFocusFromTouch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                startResult(p0!!)
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                    showFragment(0)
                return true
            }
        })
        return true
    }


    public fun startResult(string: String) {
        if (searResultFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(searResultFragment)
        }

        searResultFragment = SearchResultFragment(string)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout,searResultFragment).commit()
        showFragment(1)
        runOnIoThread {
            val dao =  AndroidDataBase.getInstence(this).getRecentSearchDao()
                    dao.isExist(string) ?: dao.insert(RecentSearch(string))
        }
    }


    public fun showFragment(position: Int) {
        when (position) {
            0 -> {
                supportFragmentManager.beginTransaction().hide(searResultFragment)
                        .show(searchTagFragment)
                        .commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction().hide(searchTagFragment)
                        .show(searResultFragment)
                        .commit()
            }
        }

    }
}
