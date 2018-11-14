package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import android.view.View
import android.widget.ImageView
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.bean.CollectArticleBean
import com.example.administrator.wanandroid.database.ReadPlanArticle
import com.example.administrator.wanandroid.database.StudyProject
import com.example.library.paging.BasePagingAdapter
import com.example.library.utils.glide.ImageLoader

/**
 * @author  : Alex
 * @date    : 2018/08/30
 * @version : V 2.0.0
 */
class ReadPlanArticleAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) : BasePagingAdapter<T>(diffCallback) {


    var itemClickListener: OnItemClickListener<T>? = null

    override fun bind(holder: ViewHolder, t: T, position: Int) {
        if (t is ReadPlanArticle ) {
            holder.setText(R.id.tvTitle, t.title)
            holder.setText(R.id.tvChapter, t.chapterName)
            holder.setText(R.id.tvTeam, t.author)
        }else if (t is StudyProject){
            holder.setText(R.id.tvTitle, t.title)
            holder.setText(R.id.tvChapter, t.chapterName)
            holder.setText(R.id.tvTeam, t.author)
        }

        holder.view.setOnClickListener {
            itemClickListener?.itemClick(t, position)
        }

    }

    override fun getItemLayout() = R.layout.item_view

}