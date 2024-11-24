package com.example.hw3_viewpager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hw3_viewpager.R
import com.example.hw3_viewpager.adapter.ViewPagerAdapter
import com.example.hw3_viewpager.databinding.FragmentQuestionnaireBinding
import com.example.hw3_viewpager.databinding.FragmentViewPagerBinding
import com.example.hw3_viewpager.repository.QuestionsRepository
import com.google.android.material.snackbar.Snackbar


class ViewPagerFragment : Fragment() {

    private var viewBinding: FragmentViewPagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(fragmentManager = parentFragmentManager, lifecycle = this.lifecycle)
        viewBinding?.run {
            viewPager.adapter = adapter
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val currentPosition = position + 1
                    buttonBack.isEnabled = currentPosition != 1

                    val isLastQuestion = currentPosition == QuestionsRepository.items.size - 1
                    buttonNext.isInvisible = isLastQuestion
                    buttonComplete.isVisible = isLastQuestion

                    questionNumberTv.text = "$currentPosition/${QuestionsRepository.items.size}"
                }
            })

            buttonBack.setOnClickListener {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }

            buttonNext.setOnClickListener {
                viewPager.setCurrentItem( viewPager.currentItem + 1, true)
            }

            buttonComplete.setOnClickListener {
                Snackbar.make(viewPager, "Результаты сохранены", Snackbar.LENGTH_SHORT).show()
            }


        }
        super.onViewCreated(view, savedInstanceState)
    }
}