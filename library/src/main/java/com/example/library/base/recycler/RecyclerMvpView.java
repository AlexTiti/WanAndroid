package com.example.library.base.recycler;

import com.example.library.base.mvp.Contract;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/22
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public interface RecyclerMvpView<T> extends Contract.ViewMvp<T> {
    /**
     *
     *下拉加载
     * @param t 数据类型
     */
    void showLoadMoreData(T t);

    /**
     * 下拉加载失败
     * @param errorMessage
     */
    void showLoadMoreError(String errorMessage);

    /**
     * 刷新
     * @param t
     */
    void showRefreshData(T t);

    /**
     * 刷新失败
     * @param errorMessage
     */
    void showRefreshError(String errorMessage);

    /**
     * 单个状态改变
     * @param position
     */
    void notifyItemChange(int position);

    /**
     * 网络错误
     */
     void showNetWorkError();


}
