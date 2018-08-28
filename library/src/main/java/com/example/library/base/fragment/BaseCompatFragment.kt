package com.example.library.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.library.global.GlobalApplication
import com.example.library.utils.AppUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author Administrator
 */
abstract class BaseCompatFragment : Fragment() {

    protected var TAG: String? = null
    protected var mContext: Context? = null
    protected lateinit var mActivity: Activity
    protected lateinit var mApplication: GlobalApplication
    private var binder: Unbinder? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoadData()
    }

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false


    /**
     * 获取xml文件
     *
     * @return
     */
    @get:LayoutRes
    abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            loadDataPrepare()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder = ButterKnife.bind(this, view)
        mCompositeDisposable = CompositeDisposable()
        isViewPrepare = true
        getBundle(arguments)
        prepare()
        initUI(view, savedInstanceState)
        loadDataPrepare()
    }

    private fun loadDataPrepare() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoadData()
            hasLoadData = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binder != null) {
            binder!!.unbind()
        }
        clearCompositeDisposable()

    }

    /**
     * 添加订阅
     *
     * @param disposable 订阅的对象
     */
    fun addCompositeDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)

    }

    /**
     * 取消订阅
     */
    private fun clearCompositeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    /**
     * 检测网络问题
     */
    fun checkNetWork() {
    }

    /**
     * 得到Activity传进来的值
     */
    private fun getBundle(bundle: Bundle?) {}

    /**
     * 初始化UI
     *
     * @param view               onCreateView()
     * @param savedInstanceState Bundle
     */
    abstract fun initUI(view: View, savedInstanceState: Bundle?)

    /**
     * 在监听器之前把数据准备好
     */
    open fun prepare() {
        mContext = AppUtils.context
        mApplication = mActivity.application as GlobalApplication
    }

    abstract fun lazyLoadData()
}
