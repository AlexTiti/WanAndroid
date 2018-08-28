package com.example.library.base.mvp;

import android.os.Bundle;

import com.example.library.base.BaseCompatActivity;


/**
 * @author Administrator
 */
public abstract class BaseActivityMvp<V extends Contract.ViewMvp,P extends BasePresenterMvp> extends BaseCompatActivity {

  public P present;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = createPresent();
        if (present != null) {
            present.onAttach((V) this);
        }
        initDataFromServer();
    }

    /**
     * 获取数据
     */
    public abstract void initDataFromServer();

    /**
     * 获取Present
     * @return P
     */
    public P getPresent() {
        return present;
    }

    /**
     * 创建Present
     * @return P
     */
    protected abstract P createPresent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (present != null) {
            present.disAttach();
        }
    }
}
