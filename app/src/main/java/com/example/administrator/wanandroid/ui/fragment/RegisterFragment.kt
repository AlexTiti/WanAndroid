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
import com.example.administrator.wanandroid.R
import com.example.administrator.wanandroid.model.LoginModel
import com.example.administrator.wanandroid.respository.LoginResposity
import com.example.administrator.wanandroid.ui.MainActivity
import com.example.library.utils.PreferencesUtil
import com.example.library.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var model: LoginModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        model = ViewModelProviders.of(activity!!, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginModel(LoginResposity()) as T
            }

        })[LoginModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {

            val account = tie_phone_register.text?.trim().toString()
            val password = tie_pswd_register.text?.trim().toString()
            val password_re = tie_pswd_register_re.text?.trim().toString()

            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_re)) {
                ToastUtils.showToast(getString(R.string.login_empty))
            } else {
                model.register(account, password, password_re)
            }
        }


        model.register.observe(this, Observer {
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


    companion object {
        fun newInstance(): RegisterFragment {
            val fragment = RegisterFragment()
            return fragment
        }
    }

}
