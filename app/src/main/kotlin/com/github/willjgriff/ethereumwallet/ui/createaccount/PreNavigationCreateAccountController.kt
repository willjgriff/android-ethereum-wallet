package com.github.willjgriff.ethereumwallet.ui.createaccount

import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationController
import kotlinx.android.synthetic.main.controller_create_account_pre_navigation.view.*

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class PreNavigationCreateAccountController : Controller(), CreateAccountCompletedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_create_account_pre_navigation, container, false)

        addCreateAccountController(view)
        (view.controller_create_account_pre_nav_toolbar as Toolbar).setTitle(R.string.controller_create_account_title)

        return view
    }

    private fun addCreateAccountController(view: View) {
        val createAccountController = CreateAccountController()
        createAccountController.targetController = this
        getChildRouter(view.controller_create_account_pre_nav_container)
                .pushController(RouterTransaction.with(createAccountController))
    }

    override fun onAccountCreated() {
        router.setRoot(RouterTransaction.with(NavigationController())
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }
}