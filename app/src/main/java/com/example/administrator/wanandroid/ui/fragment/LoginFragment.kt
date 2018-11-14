package com.example.administrator.wanandroid.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.model.LoginModel
import com.example.administrator.wanandroid.respository.LoginResposity
import com.example.administrator.wanandroid.ui.MainActivity
import com.example.library.utils.PreferencesUtil
import com.example.library.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

@Suppress("UNCHECKED_CAST")

class LoginFragment : Fragment() {

    private lateinit var model: LoginModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        model = ViewModelProviders.of(activity!!, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginModel(LoginResposity()) as T
            }

        })[LoginModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {

            val account = tie_phone_login.text?.trim().toString()
            val password = tie_pswd_login.text?.trim().toString()

            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                ToastUtils.showToast(getString(R.string.login_empty))
            } else {
                model.login(account, password)
            }
        }

        tv_register.setOnClickListener {
            Navigation.findNavController(tv_register).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        model.loginState.observe(this, Observer {
            if (it?.errorCode == 0) {
                startActivity(Intent(activity, MainActivity::class.java))
                var userId: Int by PreferencesUtil("userId", 0)
                var userLogin: Boolean by PreferencesUtil<Boolean>("login", false)
                var userName: String by PreferencesUtil<String>("userName", "Android")
//                var userLogin : Boolean by PreferencesUtil<Boolean>("login",false)
                userId = it.data?.id!!
                userName = it.data?.username!!
                userLogin = true
                activity?.finish()
            } else {
                ToastUtils.showToast(it?.errorMsg!!)
            }

        })
    }


}
