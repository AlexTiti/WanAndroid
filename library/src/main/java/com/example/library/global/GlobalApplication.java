package com.example.library.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.administrator.login.aop.login.ILoginView;
import com.example.administrator.login.aop.login.LoginAssistant;
import com.example.library.utils.PreferencesUtil;


/**
 * Created by Horrarndoo on 2017/9/1.
 * <p>
 * 全局Application
 */

public class GlobalApplication extends Application {
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;


    ILoginView iLoginView = new ILoginView() {
        @Override
        public boolean isLogin() {
            return false;
        }

        @Override
        public void login(int userDefine) {
            Toast.makeText(GlobalApplication.this, "需要在GlobalApplication设置逻辑", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void exitLogin() {
            Toast.makeText(GlobalApplication.this, "需要在GlobalApplication设置逻辑", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        LoginAssistant.Companion.getInstance().setView(iLoginView);
        PreferencesUtil.Companion.get(this);

    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }
}
