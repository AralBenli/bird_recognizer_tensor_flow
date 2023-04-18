package com.example.flowerrecognizer.ui.pages.register


import com.example.flowerrecognizer.databinding.FragmentRegisterBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity


class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun getViewBinding(): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
    }


}