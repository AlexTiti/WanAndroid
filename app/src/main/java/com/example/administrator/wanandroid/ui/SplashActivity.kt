package com.example.administrator.wanandroid.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.library.utils.PreferencesUtil
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dispose = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap {
                    val isLogin by PreferencesUtil<Boolean>("login", false)
                    if (isLogin) {
                        return@flatMap Observable.just(1L)
                    } else {
                        return@flatMap Observable.just(0L)
                    }
                }
                .subscribe {
                    if (it == 1L) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    finish()
                }

    }

}
