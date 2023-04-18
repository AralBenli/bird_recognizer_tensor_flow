package com.example.flowerrecognizer.ui.pages.onboarding


import com.example.flowerrecognizer.databinding.FragmentOnboardingBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun getViewBinding(): FragmentOnboardingBinding
    = FragmentOnboardingBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
    }
}