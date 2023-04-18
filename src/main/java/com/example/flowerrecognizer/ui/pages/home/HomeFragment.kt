package com.example.flowerrecognizer.ui.pages.home


import com.example.flowerrecognizer.databinding.FragmentHomeBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(true)
    }

}