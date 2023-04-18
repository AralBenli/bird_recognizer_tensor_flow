package com.example.flowerrecognizer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by AralBenli on 18.04.2023.
 */
abstract class BaseFragment<VB : ViewBinding> :
    Fragment() {
        lateinit var binding: VB

    abstract fun getViewBinding(): VB
    abstract fun initViews()
    private var savedInstanceView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceView == null) {
            onCreateViewBase()
            binding = getViewBinding()
            savedInstanceView = binding.root
            savedInstanceView
        } else {
            savedInstanceView
        }


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    open fun onCreateViewBase(){}
    open fun observer(){}
}

