package com.example.birdRecognizer.app.ui.pages.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.birdRecognizer.databinding.OnBoardingItemBinding

/**
 * Created by AralBenli on 20.04.2023.
 */
class OnBoardingAdapter constructor(
    val context: Context,
    private val onBoardingList: List<OnBoardingItem>
) : RecyclerView.Adapter<OnBoardingAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: OnBoardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(imageId: Int,title:String,subtitle:String) {
            binding.introTitle.text = title
            binding.introDescription.text = subtitle
            binding.introImg.setImageResource(imageId)
        }
    }

    override fun getItemCount(): Int = onBoardingList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = OnBoardingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(onBoardingList[position].imageId,onBoardingList[position].title,onBoardingList[position].description)
    }
}