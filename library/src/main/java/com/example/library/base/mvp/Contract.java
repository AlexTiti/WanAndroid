package com.example.library.base.mvp;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @version  :   V 1.0.9
 */

public interface Contract {

    interface ModeMvp {

    }

    interface ViewMvp<T> {
        /**
         * 请求成功
         *
         * @param t T
         */
        void loadSuccess(T t);

        /**
         * 请求失败
         *
         * @param s 错误信息
         */
        void loadFailed(String s);


    }

    interface PresenterMvp {

    }
}
