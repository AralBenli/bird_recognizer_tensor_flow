package com.example.birdRecognizer.app.ui.pages.login


import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.app.ui.base.BaseFragment
import com.example.birdRecognizer.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun initViews() {
        navigation()
        firebaseAuth = FirebaseAuth.getInstance()

    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            findNavController().navigate(R.id.loginToHome)
        }
    }

    private fun navigation() {
        /**
         * navigation to register.
         * */
        binding.dontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.loginToReg)
        }
        /**
         * navigation to forget password.
         * */
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.loginToForgetPassword)
        }

        /**
         * if email and password matches login ; sign in method.
         * */
        binding.signinButton.setOnClickListener {
            loginMethod()
        }
    }

    private fun loginMethod() {
        val email = binding.emailEditText.editText?.text.toString().trim()
        val password = binding.passwordEditText.editText?.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser!!.isEmailVerified) {
                        showCustomToast(
                            Status.Success,
                            "Login Successful",
                            this@LoginFragment
                        )
                        findNavController().navigate(R.id.loginToHome)
                    } else {
                        showCustomToast(
                            Status.Failed,
                            "Please verify your email first",
                            this@LoginFragment
                        )
                    }
                } else {
                    showCustomToast(
                        Status.Failed,
                        it.exception.toString(),
                        this@LoginFragment
                    )
                }
            }
        } else {
            showCustomToast(
                Status.Failed,
                "Please enter email and password",
                this@LoginFragment
            )
        }
    }
}