package ru.itis.clientserverapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.clientserverapp.nav.NavImpl
import ru.itis.clientserverapp.nav.NavMainImpl
import ru.itis.clientserverapp.navigation.Nav
import ru.itis.clientserverapp.navigation.NavMain
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    @Singleton
    fun bindNavToImpl(impl: NavImpl): Nav

    @Binds
    @Singleton
    fun bindNavMainToImpl(impl: NavMainImpl): NavMain
}
