package com.example.administrator.wanandroid.adapter

import android.support.v7.util.DiffUtil
import android.view.View
import android.widget.ImageView
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.bean.ArticleBean
import com.example.library.paging.BasePagingAdapter
import com.example.library.utils.glide.ImageLoader

/**
 * @author  : Alex
 * @date    : 2018/08/30
 * @version : V 2.0.0
 */
class ArticleAdapter(diffCallback: DiffUtil.ItemCallback<ArticleBean>) : BasePagingAdapter<ArticleBean>(diffCallback) {


    var itemClickListener: OnItemClickListener<ArticleBean>? = null

    override fun bind(holder: ViewHolder, t: ArticleBean, position: Int) {
        holder.setText(R.id.tvTitle, t.title)
        holder.setText(R.id.tvChapter, t.chapterName)
        holder.setText(R.id.tvTime, t.niceDate)
        holder.setText(R.id.tvTeam, t.author)
        val mEnvelopeIv: ImageView = holder.getView(R.id.iv_home_show) as ImageView
        if (t.envelopePic == null || t.envelopePic!!.isEmpty()) {
            mEnvelopeIv.visibility = View.GONE
        } else {
            mEnvelopeIv.visibility = View.VISIBLE
            ImageLoader.loadImageFromUrlThumb(context, t.envelopePic, 0.5f, mEnvelopeIv)
        }
        holder.view.setOnClickListener {
            itemClickListener?.itemClick(t, position)
        }
    }

    override fun getItemLayout() = R.layout.item_view

}