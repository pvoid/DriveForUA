package com.github.pvoid.driveforua

import androidx.fragment.app.Fragment
import com.facebook.Profile
import com.facebook.login.Login

enum class NavigationTargets {
    Login,
    UserType,
    DriverInfo,
    PassengerInfo
}

class Navigator {

    interface NavigationCallback {
        fun open(fragment: Fragment)
    }

    companion object {
        private var callback: NavigationCallback? = null

        fun registerCallback(callback: NavigationCallback) {
            Navigator.callback = callback
        }

        fun navigateTo(target: NavigationTargets) {
            when (target) {
                NavigationTargets.Login -> LoginFragment()
                NavigationTargets.UserType -> {
                    if (Profile.getCurrentProfile() == null) {
                        // TODO: Add rate limit?
                        Profile.fetchProfileForCurrentAccessToken()
                    }
                    UserTypeFragment()
                }
                NavigationTargets.DriverInfo -> DriverFragment()
                NavigationTargets.PassengerInfo -> PassengerFragment()
            }.let {
                callback?.open(it)
            }
        }
    }
}