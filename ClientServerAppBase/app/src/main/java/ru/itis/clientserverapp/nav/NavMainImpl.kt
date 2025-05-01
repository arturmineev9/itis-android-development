package ru.itis.clientserverapp.nav

import ru.itis.clientserverapp.navigation.Nav
import ru.itis.clientserverapp.navigation.NavMain
import ru.itis.clientserverapp.app.R
import javax.inject.Inject


class NavMainImpl @Inject constructor(
    private val navigatorDelegate: NavigatorDelegate,
) : NavMain {

    private var parent: Nav? = null

    override fun initNavMain(parent: Nav) {
        this.parent = parent
    }

    override fun goToDogDetailsPage() {
        navigatorDelegate.navigate(action = R.id.action_global_dog_details)
    }

}