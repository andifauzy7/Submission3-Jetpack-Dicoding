package com.example.cinema.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.cinema.R
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.ui.splash.SplashActivity
import com.example.cinema.utils.Profile

class ProfileFragment : Fragment() {
    private lateinit var fragmentProfileFragment: FragmentProfileBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentProfileFragment = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return fragmentProfileFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences(Profile.KEY_APP, Context.MODE_PRIVATE)
        val name = sharedPref?.getString(Profile.KEY_NAME, Profile.NULL_VALUE)
        val gender = sharedPref?.getString(Profile.KEY_GENDER, Profile.NULL_VALUE)
        fragmentProfileFragment.tvNameProfile.text = name
        fragmentProfileFragment.tvGenderProfile.text = gender

        if(gender == Profile.MALE_VALUE){
            Glide.with(this)
                    .load(R.drawable.avatar_male)
                    .centerCrop()
                    .transform(RoundedCorners(16))
                    .into(fragmentProfileFragment.profileImage)
        } else {
            Glide.with(this)
                    .load(R.drawable.avatar_female)
                    .centerCrop()
                    .transform(RoundedCorners(16))
                    .into(fragmentProfileFragment.profileImage)
        }

        fragmentProfileFragment.btnLogout.setOnClickListener {
            sharedPref?.edit()?.clear()?.apply()
            val i = Intent(context, SplashActivity::class.java)
            activity?.startActivity(i)
            activity?.finish()
        }
    }
}