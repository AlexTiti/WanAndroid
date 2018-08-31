package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import android.widget.ImageView
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.library.paging.BasePagingAdapter

/**
 * @author  : Alex
 * @date    : 2018/08/30
 * @version : V 2.0.0
 */
class ArticleAdapter : BasePagingAdapter<ArticleBean> {

    interface ItemClickListener {
        fun itemClick(t: ArticleBean)
    }

    var itemClickListener: ItemClickListener? = null

    fun setListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun bind(holder: ViewHolder, t: ArticleBean) {
        holder.setText(R.id.tvTitle, t.title)
        holder.setText(R.id.tvChapter, t.chapterName)
        holder.setText(R.id.tvTime, t.publishTime.toString())
        holder.setText(R.id.tvTeam, t.author)
        holder.view.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            itemClickListener?.itemClick(t)
        }
    }

    constructor(diffCallback: DiffUtil.ItemCallback<ArticleBean>) : super(diffCallback)


    override fun getItemLayout() = R.layout.item_view

}