package com.example.birdRecognizer.app.ui.pages.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.app.SharedPreferencesManager
import com.example.birdRecognizer.databinding.FragmentSplashBinding
import com.example.birdRecognizer.app.ui.base.BaseFragment


class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun getViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun initViews() {
        splash()
    }


    private fun splash() {
        Handler(Looper.getMainLooper()).postDelayed({
            view?.post {
                if (SharedPreferencesManager.getBoolean(requireContext(), "skipped", false)) {
                    findNavController().navigate(R.id.splashToLogin)
                } else {
                    findNavController().navigate(R.id.splashToOnboarding)
                }
            }
        }, 3000)
    }
}




