package com.example.flowerrecognizer.ui.pages.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.flowerrecognizer.R
import com.example.flowerrecognizer.databinding.FragmentSplashBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity
import com.example.flowerrecognizer.ui.utils.SharedPreferencesManager


class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun getViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
        splash()
    }


    private fun splash() {
        Handler(Looper.getMainLooper()).postDelayed({
            view?.post {
/*
                if (SharedPreferencesManager.getBoolean(requireContext(), "skipped", false)) {
*/
                    findNavController().navigate(R.id.splashToLogin)
                /*} else {
                    findNavController().navigate(R.id.splashToOnboarding)
                }*/
            }
        }, 3000)
    }
}




