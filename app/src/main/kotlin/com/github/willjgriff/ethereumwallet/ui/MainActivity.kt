package com.github.willjgriff.ethereumwallet.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationController
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.PreNavigationCreateAccountController
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class MainActivity : AppCompatActivity() {

    @Inject lateinit var addressManager: AddressManager
    private lateinit var router: Router

    init {
        AppInjector.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, activity_main_controller_container, savedInstanceState)
        if (!router.hasRootController()) {
            checkForAccountAndSetRoot()
        }
    }

    private fun checkForAccountAndSetRoot() {
        if (addressManager.hasAddress()) {
            setConductorRoot(NavigationController())
        } else {
            setConductorRoot(PreNavigationCreateAccountController())
        }
    }

    private fun setConductorRoot(controller: Controller) {
        router.setRoot(RouterTransaction.with(controller)
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}