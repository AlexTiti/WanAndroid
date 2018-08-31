package com.example.administrator.wanandroid.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.library.base.BaseCompatActivity
import kotlinx.android.synthetic.main.toolbar_.*

const val EXTRAL_SEARCH = "extral_text"

class SearchActivity : BaseCompatActivity() {
    override fun onErrorViewClick(v: View?) {

    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.title = "Search"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    var queryText = "View"
    override fun getLayoutId() = R.layout.activity_search


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu?.findItem(R.id.searchView)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = true
        searchView.isQueryRefinementEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                queryText = p0!!
                return false
            }

            override fun onQueryTextChange(p0: String?) = false

        })
        return true
    }

    override fun onSearchRequested(): Boolean {
        val bundle = Bundle()
        bundle.putString(EXTRAL_SEARCH, queryText)
        startSearch(null, false, bundle, false)
        return true
    }

}
