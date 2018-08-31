package com.example.library.paging

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author  : Alex
 * @date    : 2018/08/21
 * @version : V 2.0.0
 */
abstract class BasePagingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, BasePagingAdapter<T>.ViewHolder>(diffCallback) {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder, getItem(position)!!)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val map = mutableMapOf<Int, View>()

        fun getView(id: Int): View? {
            var viewId = map[id]
            if (viewId == null) {
                viewId = view.findViewById(id)
                map[id] = viewId
            }
            return viewId
        }

        fun setText(id: Int, string: String?) {
            val textView = getView(id)
            if (textView is TextView) {
                textView.text = string
            }
        }

    }


    abstract fun bind(holder: ViewHolder, t: T)

    abstract fun getItemLayout(): Int
}