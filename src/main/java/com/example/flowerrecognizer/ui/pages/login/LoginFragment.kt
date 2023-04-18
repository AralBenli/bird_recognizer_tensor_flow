package com.example.flowerrecognizer.ui.pages.login


import androidx.navigation.fragment.findNavController
import com.example.flowerrecognizer.R
import com.example.flowerrecognizer.databinding.FragmentLoginBinding
import com.example.flowerrecognizer.ui.base.BaseFragment
import com.example.flowerrecognizer.ui.pages.main.MainActivity


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
        navigation()
    }


    private fun navigation() {
        binding.dontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.loginToReg)
        }

        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.loginToForgetPassword)
        }

        binding.signinButton.setOnClickListener {
            findNavController().navigate(R.id.loginToHome)
        }
    }
}