package com.example.flowerrecognizer.ui.pages.forget_password


import com.example.flowerrecognizer.databinding.FragmentForgetPasswordBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity


class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {
    override fun getViewBinding(): FragmentForgetPasswordBinding =
        FragmentForgetPasswordBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
    }


}