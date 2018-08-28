package com.example.library.base.mvp;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @version  :   V 1.0.9
 */

public abstract class BasePresenterMvp< V extends Contract.ViewMvp, M extends Contract.ModeMvp>  {

    public V mView;
    public M model;

    public void onAttach(V mView) {
        this.mView = mView;
        this.model = createModel();
    }

    public void disAttach() {

        if (mView != null) {
            mView = null;
        }
        if (model != null){
            model = null;
        }
    }

    /**
     * 创建Model
     * @return 返回Model
     */
    public abstract M createModel();

}
