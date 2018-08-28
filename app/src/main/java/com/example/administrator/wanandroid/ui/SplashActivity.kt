package com.example.administrator.wanandroid.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {
                    startActivity(Intent(this, MainActivity::class.java))
                }

    }

//    override fun getLayoutId(): Int = R.layout.activity_splash


}
