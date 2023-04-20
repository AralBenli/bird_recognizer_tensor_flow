package com.example.birdRecognizer.app.ui.pages.register


import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.databinding.FragmentRegisterBinding
import com.example.birdRecognizer.app.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun getViewBinding(): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)

    override fun initViews() {
        navigation()
        firebaseAuth = FirebaseAuth.getInstance()

    }

    private fun navigation() {
        /**
         *  if you don't have an account , register area
         * */
        binding.registerButton.setOnClickListener {

            val email = binding.emailAddress.editText?.text.toString().trim()
            val password = binding.password.editText?.text.toString().trim()
            val rePassword = binding.repassword.editText?.text.toString().trim()
            val name = binding.fullName.editText?.text.toString().trim()

            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
                if (password == rePassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                    showCustomToast(
                                        Status.Success,
                                        "Account created successfully. Please check your email to verify",
                                        this@RegisterFragment
                                    )
                                    findNavController().navigate(R.id.regToLogin)
                                }
                                    ?.addOnFailureListener {
                                        showCustomToast(
                                            Status.Failed,
                                            it.toString(),
                                            this@RegisterFragment
                                        )
                                    }
                            } else {
                                showCustomToast(
                                    Status.Failed,
                                    task.exception.toString(),
                                    this@RegisterFragment
                                )
                            }
                        }
                } else {
                    showCustomToast(
                        Status.Failed,
                        "Password is not matching !",
                        this@RegisterFragment
                    )
                }
            } else {
                showCustomToast(
                    Status.Failed,
                    "Empty fields are not allowed !",
                    this@RegisterFragment
                )
            }
        }
        /**
         *  if you already have an account
         * */
        binding.haveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.regToLogin)
        }
    }
}