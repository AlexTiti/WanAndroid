package com.example.administrator.wanandroid.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.ui.fragment.LoginFragment
import com.example.administrator.wanandroid.ui.fragment.RegisterFragment
import com.example.library.base.BaseCompatActivity

class LoginActivity : BaseCompatActivity() {

    override fun onErrorViewClick(v: View?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) savedInstanceState.let {
            loginFragment = fragmentManager.getFragment(it, LoginFragment::class.java.simpleName) as LoginFragment
            registerFragment = fragmentManager.getFragment(it, registerFragment!!::class.java.simpleName) as RegisterFragment
        } else {
            loginFragment = LoginFragment.newInstance()
            registerFragment = RegisterFragment.newInstance()
        }

        if (!loginFragment!!.isAdded) {
            with(fragmentManager) {
                beginTransaction()
                        .add(R.id.container, loginFragment!!, LoginFragment::class.java.simpleName)
                        .commit()
            }
        }
        if (!registerFragment!!.isAdded) {
            with(fragmentManager) {
                beginTransaction()
                        .add(R.id.container, registerFragment!!, RegisterFragment::class.java.simpleName)
                        .commit()
            }
        }
        showHideFragment(0)
    }

    override fun getLayoutId() = R.layout.fragment_cantiner


    var loginFragment: LoginFragment? = null
    var registerFragment: RegisterFragment? = null

    val fragmentManager by lazy {
        supportFragmentManager
    }


    fun showHideFragment(position: Int) {
        with(fragmentManager) {
            when (position) {
                0 -> {
                    beginTransaction().show(loginFragment!!).hide(registerFragment!!).commit()
                }
                else -> {
                    beginTransaction().show(registerFragment!!).hide(loginFragment!!).commit()
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        if (loginFragment!!.isAdded) {
            fragmentManager.putFragment(outState!!, LoginFragment::class.java.simpleName, loginFragment!!)
        }
        if (registerFragment!!.isAdded) {
            fragmentManager.putFragment(outState!!, LoginFragment::class.java.simpleName, registerFragment!!)
        }

    }


}
