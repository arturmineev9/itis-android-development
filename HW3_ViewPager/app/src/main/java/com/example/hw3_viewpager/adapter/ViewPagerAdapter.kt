package com.example.hw3_viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hw3_viewpager.fragments.QuestionnaireFragment
import com.example.hw3_viewpager.repository.QuestionsRepository

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount() = QuestionsRepository.items.size

    override fun createFragment(position: Int): Fragment {
        return QuestionnaireFragment.getInstance(position)
    }
}