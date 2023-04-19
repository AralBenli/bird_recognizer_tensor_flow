package com.example.tensorflowlearn.app.ui.pages.forget_password


import androidx.navigation.fragment.findNavController
import com.example.tensorflowlearn.R
import com.example.tensorflowlearn.databinding.FragmentForgetPasswordBinding
import com.example.tensorflowlearn.app.ui.base.BaseFragment
import com.example.tensorflowlearn.app.ui.pages.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun getViewBinding(): FragmentForgetPasswordBinding =
        FragmentForgetPasswordBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(false)
        firebaseAuth = FirebaseAuth.getInstance()
        navigation()
    }

    private fun navigation() {

        binding.forgotPassSentButton.setOnClickListener {
            val email = binding.forgotEmailAddress.editText?.text.toString().trim()
            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful){
                        showCustomToast(Status.Success, "Mail has been sent", this@ForgetPasswordFragment)
                        findNavController().navigate(R.id.forgetPasswordToLogin)
                    }else{
                        showCustomToast(Status.Failed, "Invalid Email", this@ForgetPasswordFragment)
                    }
                }
            }else{
                showCustomToast(Status.Failed, "Invalid Email", this@ForgetPasswordFragment)
            }
        }

        /**
         * cancel directly navigates to login.
         * */
        binding.forgotPassCancelButton.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordToLogin)
        }
    }


}