package com.example.administrator.wanandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.bean.CategoryBean
import com.example.administrator.wanandroid.listener.OnItemClickListener
import com.example.administrator.wanandroid.listener.OnItemNavigationClickListener
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * @author  : Alex
 * @date    : 2018/10/19
 * @version : V 2.0.0
 */
class CategoryAdapter( var itemClickListener: OnItemNavigationClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    var list : List<CategoryBean>? = null
    set(value) {
        field = value
       notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.navigation_item,p0,false),itemClickListener)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val bean = list!!.get(p1)
        holder.title.text = bean.name
        holder.flowLayout.adapter = object :TagAdapter<CategoryBean>(bean.children){
            override fun getView(parent: FlowLayout?, position: Int, child: CategoryBean?): View {
                val textView = LayoutInflater.from(parent?.getContext()).inflate(R.layout.item_flow_layout, holder.flowLayout, false) as TextView

                textView.text = child?.name

                holder.flowLayout.setOnTagClickListener { view, position, parent ->
                    itemClickListener?.itemClick(view, position, parent, bean.children!!)
                    true
                }
                return textView
            }
        }
    }


    inner class ViewHolder(val v: View, itemClickListener: OnItemNavigationClickListener ): RecyclerView.ViewHolder(v) {

        lateinit var title : TextView
        lateinit var flowLayout: TagFlowLayout

        init {
           title = v.findViewById(R.id.tv_navigation_tittle)
            flowLayout = v.findViewById(R.id.flow_layout)
        }



    }

}