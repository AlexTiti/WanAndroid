package com.example.library.base.mvp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.library.base.BaseCompatActivity;
import com.example.library.base.fragment.BaseCompatFragment;


/**
 * <pre>
 *
 *   @author   :   Alex
 *   @version  :   V 1.0.9
 */

public abstract class BaseFragmentMvp<V extends Contract.ViewMvp, P extends BasePresenterMvp>
        extends BaseCompatFragment{

    private P present;


    @Override
    public void prepare() {
        super.prepare();
        present = createPresent();
        if (present != null) {
            present.onAttach((V) this);
        }
    }

    public P getPresent() {
        return present;
    }

    /**
     * 创建Present
     *
     * @return 继承的Present
     */
    protected abstract P createPresent();

    @Override
    public void onDestroy() {
        super.onDestroy();
        present.disAttach();
    }

    private void startActivityBeforeLOLLIPOP(Activity activity, Class c) {
        startActivity(new Intent(activity, c));
    }

    /**
     * start Activity
     *
     * @param activity Activity 对象
     * @param c        字节码文件
     */
    public void startActivityUseLOLLIPOP(Activity activity, Class c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
            startActivity(new Intent(activity, c), options.toBundle());
        } else {
            startActivityBeforeLOLLIPOP(activity, c);
        }
    }


    /**
     * Share View
     *
     * @param activity  Activity 对象
     * @param c         Class字节码文件
     * @param view      View
     * @param transName 共享控件设置的字符串
     */
    public void startActivityUseShare(Activity activity, Class c, View view, String transName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, transName);
            startActivity(new Intent(activity, c), options.toBundle());
        } else {
            startActivityBeforeLOLLIPOP(activity, c);
        }
    }

    public void startNewActivity(@NonNull Class<?> clz) {
        ((BaseCompatActivity) getMActivity()).startActivity(clz);
    }

    public void startNewActivity(@NonNull Class<?> clz, Bundle bundle) {
        ((BaseCompatActivity) getMActivity()).startActivity(clz, bundle);
    }


    public void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {
        ((BaseCompatActivity) getMActivity()).startActivityForResult(clz, bundle, requestCode);
    }

    public Activity getBindActivity() {
        return getMActivity();
    }
}
