package ru.itis.clientserverapp.base_feature

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

@dagger.hilt.android.AndroidEntryPoint
open class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId)
