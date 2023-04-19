package com.example.tensorflowlearn.app.ui.pages.onboarding


import com.example.tensorflowlearn.databinding.FragmentOnboardingBinding
import com.example.tensorflowlearn.app.ui.base.BaseFragment
import com.example.tensorflowlearn.app.ui.pages.main.MainActivity


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun getViewBinding(): FragmentOnboardingBinding
    = FragmentOnboardingBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
    }
}