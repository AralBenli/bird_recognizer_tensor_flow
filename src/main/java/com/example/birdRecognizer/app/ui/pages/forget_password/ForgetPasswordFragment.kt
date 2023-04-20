package com.example.birdRecognizer.app.ui.pages.forget_password


import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.app.ui.base.BaseFragment
import com.example.birdRecognizer.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth



class ForgetPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun getViewBinding(): FragmentForgetPasswordBinding =
        FragmentForgetPasswordBinding.inflate(layoutInflater)

    override fun initViews() {
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