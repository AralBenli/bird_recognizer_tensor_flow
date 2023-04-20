package com.example.birdRecognizer.app.ui.pages.onboarding


import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.birdRecognizer.R
import com.example.birdRecognizer.app.SharedPreferencesManager
import com.example.birdRecognizer.databinding.FragmentOnboardingBinding
import com.example.birdRecognizer.app.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    var onBoardingAdapter: OnBoardingAdapter? = null
    var position = 0
    override fun getViewBinding(): FragmentOnboardingBinding =
        FragmentOnboardingBinding.inflate(layoutInflater)

    override fun initViews() {

        val onBoardingList: MutableList<OnBoardingItem> = ArrayList()

        onBoardingList.add(
            OnBoardingItem(
                "Welcome to Bird Recognizer Application",
                "You can take a photo to a bird and you can detect which bird it is.",
                R.drawable.bird_1
            )
        )
        onBoardingList.add(
            OnBoardingItem(
                "Do you like birds , Do you want to learn more about them",
                "Also you can input photo from your local and detect the name. ",
                R.drawable.bird_2
            )
        )
        onBoardingList.add(
            OnBoardingItem(
                "We can either Search...!",
                "After find it , you can Google it. also if you long click to image you can download it.",
                R.drawable.bird_3
            )
        )

        onBoardingAdapter = OnBoardingAdapter(requireContext(), onBoardingList)
        binding.screenViewpager.adapter = onBoardingAdapter
        TabLayoutMediator(binding.tabLayoutIndicator, binding.screenViewpager)
        { _, _ -> }.attach()


        // next button click Listener
        binding.btnNext.setOnClickListener {
            position = binding.screenViewpager.currentItem
            Log.d("position", position.toString())
            if (position < onBoardingList.size - 1) {
                position++
                binding.screenViewpager.currentItem = position

                if (position == onBoardingList.size - 1)
                    setLastScreen()
                else
                    unSetLastScreen()
            } else if (position == onBoardingList.size - 1) { // when we reach to the last screen

                skipIntro()
                findNavController().navigate(R.id.onBoardingToLogin)
            }
        }
        binding.btnSkip.setOnClickListener {
            skipIntro()
            findNavController().navigate(R.id.onBoardingToLogin)
        }
    }

    private fun skipIntro() {
        SharedPreferencesManager.putBoolean(requireContext(), "skipped", true)
    }

    private fun setLastScreen() {
        binding.txtNext.text = getString(R.string.done)
    }

    private fun unSetLastScreen() {
        binding.txtNext.text = getString(R.string.next)
    }
}