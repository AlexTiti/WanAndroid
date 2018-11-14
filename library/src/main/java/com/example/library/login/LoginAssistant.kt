package com.example.administrator.login.aop.login



/**
 * @author  : Alex
 * @date    : 2018/09/03
 * @version : V 2.0.0
 */
class LoginAssistant {

    companion object {
        private var loginAssistant: LoginAssistant? = null
        fun getInstance(): LoginAssistant {
            if (loginAssistant == null) {
                synchronized(LoginAssistant::class.java) {
                    if (loginAssistant == null) {
                        loginAssistant = LoginAssistant()
                    }
                }
            }
            return loginAssistant!!
        }
    }

    var view: ILoginView? = null

}