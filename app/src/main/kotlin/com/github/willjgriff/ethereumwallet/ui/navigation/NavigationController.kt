package com.github.willjgriff.ethereumwallet.ui.navigation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController
import com.github.willjgriff.ethereumwallet.ui.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.controller_navigation.view.*

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class NavigationController : Controller(), NavigationToolbarListener {

    private val STATE_SELECTED_MENU_ITEM = NavigationToolbarListener::class.simpleName + ";STATE_SELECTED_MENU_ITEM"

    private lateinit var toolbar: Toolbar
    private lateinit var balance: TextView
    private lateinit var controllerContainer: ViewGroup
    private lateinit var bottomNavigationView: BottomNavigationView

    // TODO: See if there is a better way of doing this / find it manually each time it's needed.
    private var currentlySelectedMenuItemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_navigation, container, false)
        bindViews(view)

        // This is currently necessary because if the Activity gets reclaimed by the OS we loose the
        // position on the BottomNavigationMenu. Good job BottomNav...
        if (currentlySelectedMenuItemId != 0) {
            bottomNavigationView.menu.findItem(currentlySelectedMenuItemId).isChecked = true
        }

        if (!getChildRouter(controllerContainer).hasRootController()) {
            switchToController(TransactionsController())
        }

        setupBottomNavigationView()
        return view
    }

    private fun bindViews(view: View) {
        toolbar = view.controller_navigation_toolbar
        balance = view.controller_navigation_balance
        controllerContainer = view.controller_navigation_container
        bottomNavigationView = view.controller_navigation_bottom_navigation
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView
                .setOnNavigationItemSelectedListener { item -> bottomNavItemClicked(NavigationControllerFactory(), item) }
    }

    private fun bottomNavItemClicked(navigationControllerFactory: NavigationControllerFactory, item: MenuItem): Boolean {
        currentlySelectedMenuItemId = item.itemId
        view?.hideSoftKeyboard()
        val controller = navigationControllerFactory.getController(item)
        if (controller != null) {
            switchToController(controller)
            return true
        }
        return false
    }

    private fun switchToController(controller: Controller) {
        controller.targetController = this
        getChildRouter(controllerContainer)
                .setRoot(RouterTransaction.with(controller)
                        .pushChangeHandler(FadeChangeHandler())
                        .popChangeHandler(FadeChangeHandler()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_SELECTED_MENU_ITEM, currentlySelectedMenuItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentlySelectedMenuItemId = savedInstanceState.getInt(STATE_SELECTED_MENU_ITEM)
    }

    override fun setToolbarTitle(toolbarTitle: CharSequence) {
        // TODO: Animate the changing of this title to match the FadeChangeHandler of the Controllers.
        this.toolbar.title = toolbarTitle
    }

    override fun setBalance(balance: String) {
        this.balance.text = balance
    }
}