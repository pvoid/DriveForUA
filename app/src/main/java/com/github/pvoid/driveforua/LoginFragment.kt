package com.github.pvoid.driveforua

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

private val callbackManager = CallbackManager.Factory.create()

class LoginFragment : Fragment() {

    private val callback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            Navigator.navigateTo(NavigationTargets.UserType)
        }

        override fun onCancel() {
            // App code
        }

        override fun onError(exception: FacebookException) {
            // App code
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val loginButton = view.findViewById<LoginButton>(R.id.login_button)
        loginButton.setPermissions(listOf("email"))
        loginButton.registerCallback(callbackManager, callback)
    }
}