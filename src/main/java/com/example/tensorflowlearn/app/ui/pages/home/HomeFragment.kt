package com.example.tensorflowlearn.app.ui.pages.home


import androidx.navigation.fragment.findNavController
import com.example.tensorflowlearn.R
import com.example.tensorflowlearn.databinding.FragmentHomeBinding
import com.example.tensorflowlearn.app.ui.base.BaseFragment
import com.example.tensorflowlearn.app.ui.pages.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var firebaseAuth: FirebaseAuth


    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initViews() {
        (requireActivity() as MainActivity).backNavigation(false)
        (requireActivity() as MainActivity).actionBar(true)
        firebaseAuth = FirebaseAuth.getInstance()
        logout()
    }

    private fun logout(){
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.homeToLogin)
        }
    }

}