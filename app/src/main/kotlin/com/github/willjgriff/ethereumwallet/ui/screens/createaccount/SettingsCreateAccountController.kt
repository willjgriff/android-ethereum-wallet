package com.github.willjgriff.ethereumwallet.ui.screens.createaccount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.github.willjgriff.ethereumwallet.R
import kotlinx.android.synthetic.main.controller_create_account_settings.view.*

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SettingsCreateAccountController : Controller(), CreateAccountCompletedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_create_account_settings, container, false)
        addCreateAccountController(view)
        setToolbarTitle()
        return view
    }

    private fun addCreateAccountController(view: View) {
        val createAccountController = CreateAccountController()
        createAccountController.targetController = this
        getChildRouter(view.controller_create_account_settings_container).pushController(RouterTransaction.with(createAccountController))
    }

    private fun setToolbarTitle() {
        if (targetController is SettingsToolbarListener) {
            val settingsTitle = applicationContext?.getString(R.string.controller_create_account_title) ?: ""
            (targetController as SettingsToolbarListener).setToolbarTitle(settingsTitle)
        }
    }

    override fun onAccountCreated() {
        router.popCurrentController()
    }

    interface SettingsToolbarListener {
        fun setToolbarTitle(title: CharSequence)
    }

}