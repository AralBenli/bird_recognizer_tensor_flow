package com.example.tensorflowlearn.app.ui.pages.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.example.tensorflowlearn.R
import com.example.tensorflowlearn.databinding.FragmentSplashBinding
import com.example.tensorflowlearn.app.ui.base.BaseFragment
import com.example.tensorflowlearn.app.ui.pages.main.MainActivity


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
                    findNavController().navigate(R.id.splashToLogin)
            }
        }, 3000)
    }
}




