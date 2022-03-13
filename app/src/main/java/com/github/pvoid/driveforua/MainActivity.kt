package com.github.pvoid.driveforua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.login.LoginManager

class MainActivity : AppCompatActivity(), Navigator.NavigationCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.registerCallback(this)

        val token = AccessToken.getCurrentAccessToken()
        if (token == null || token.isExpired) {
            Navigator.navigateTo(NavigationTargets.Login)
        } else {
            Navigator.navigateTo(NavigationTargets.UserType)
        }
    }

    override fun open(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commitNow()
    }
}