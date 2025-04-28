package ru.itis.clientserverapp.mainpage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.itis.clientserverapp.base.BaseFragment
import ru.itis.clientserverapp.mainpage.R


class MainPageFragment : BaseFragment(R.layout.fragment_main_page) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

}