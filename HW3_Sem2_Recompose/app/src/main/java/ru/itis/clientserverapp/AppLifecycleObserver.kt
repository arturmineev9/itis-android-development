package ru.itis.clientserverapp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

object AppLifecycleObserver : LifecycleObserver {

    private var isForeground = false

    fun isAppInForeground(): Boolean = isForeground

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        isForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        isForeground = false
    }
}
