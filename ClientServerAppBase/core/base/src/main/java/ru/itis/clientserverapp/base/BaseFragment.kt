package ru.itis.clientserverapp.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {
}