package com.github.pvoid.driveforua

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.facebook.Profile
import com.squareup.picasso.Picasso

class UserTypeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_user_type, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button_type_passenger).setOnClickListener {
            Navigator.navigateTo(NavigationTargets.PassengerInfo)
        }

        view.findViewById<View>(R.id.button_type_driver).setOnClickListener {
            Navigator.navigateTo(NavigationTargets.DriverInfo)
        }

        updateProfileData()
    }

    private fun updateProfileData() {
        val profile = Profile.getCurrentProfile()
        if (profile != null) {
            fillProfile(profile)
        } else {
            // There is no callback in default api, so using an ugly hack by now
            view?.postDelayed({
                updateProfileData()
            }, 200)
        }
    }

    private fun fillProfile(profile: Profile) {
        view?.findViewById<TextView>(R.id.user_name)?.text = profile.name

        view?.findViewById<ImageView>(R.id.user_avatar)?.let { avatarView ->
            Picasso.get()
                .load(profile.pictureUri)
                .placeholder(com.facebook.R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(avatarView)
        }
    }
}