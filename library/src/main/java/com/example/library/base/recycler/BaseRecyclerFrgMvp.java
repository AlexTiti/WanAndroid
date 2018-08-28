package com.example.library.base.recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.R;
import com.example.library.base.mvp.BaseFragmentMvp;
import com.example.library.base.mvp.BasePresenterMvp;
import com.example.library.base.mvp.Contract;


/**
 * <pre>
 *
 *   @author   :   Alex
 *   @version  :   V 1.0.9
 */

public abstract class BaseRecyclerFrgMvp<V extends Contract.ViewMvp,P extends BasePresenterMvp> extends BaseFragmentMvp<V,P> {


    public void onLazyInitView(@Nullable Bundle savedInstanceState) {

        showLoading();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
      网络异常View
     */
        View errorView = inflater.inflate(R.layout.view_network_error, container, false);
        /*
      loadingView
     */
        View loadingView = inflater.inflate(R.layout.view_loading, container, false);
        /*
      没有内容view
     */
        View emptyView = inflater.inflate(R.layout.view_empty, container, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onErrorViewClick(v);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 网络异常view被点击时触发，由子类实现
     *
     * @param view view
     */
    protected abstract void onErrorViewClick(View view);

    /**
     * 显示加载中view，由子类实现
     */
    protected abstract void showLoading();
}
