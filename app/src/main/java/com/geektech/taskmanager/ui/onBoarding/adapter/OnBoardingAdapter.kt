package com.geektech.taskmanager.ui.onBoarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geektech.taskmanager.databinding.FragmentOnBoardingBinding
import com.geektech.taskmanager.databinding.ItemOnboardingBinding
import com.geektech.taskmanager.model.OnBoard
import com.geektech.taskmanager.utils.loadImage

class OnBoardingAdapter(private val onClick: () -> Unit) :
    Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    private val data = arrayListOf(
        OnBoard(
            "Manage Your Task", "Organize your tasks easily and quickly" +
                    " knowledge ", "https://assets4.lottiefiles.com/packages/lf20_3vc9btr6.json"
        ), OnBoard(
            "Work On Time",
            "Do not think that learning can be done at school",
            "https://assets4.lottiefiles.com/packages/lf20_gjsy1lag.json"
        ), OnBoard(
            "Get Reminder on Time", "Guided by people who professional, will add more" +
                    " knowledge ", "https://assets6.lottiefiles.com/packages/lf20_qy2hc1lc.json"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoard: OnBoard) {
            binding.tvTitle.text = onBoard.title
            binding.tvDesc.text = onBoard.desc
            binding.lottie.setAnimationFromUrl(onBoard.img.toString())
//          load image
//            binding.ivImg.loadImage(onBoard.img.toString())
            binding.btn.isVisible = adapterPosition == data.lastIndex
            binding.btn.setOnClickListener {
                onClick()
            }
        }
    }

}