package com.github.willjgriff.ethereumwallet.ui.navigation

import android.view.MenuItem
import com.bluelinelabs.conductor.Controller
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.screens.nodedetails.NodeDetailsController
import com.github.willjgriff.ethereumwallet.ui.screens.receive.ReceiveController
import com.github.willjgriff.ethereumwallet.ui.screens.send.SendController
import com.github.willjgriff.ethereumwallet.ui.screens.settings.SettingsController
import com.github.willjgriff.ethereumwallet.ui.screens.transactions.TransactionsController

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class NavigationControllerFactory {

    fun getController(item: MenuItem): Controller? {
        when (item.itemId) {
            R.id.navigation_transactions -> return TransactionsController()
            R.id.navigation_send -> return SendController()
            R.id.navigation_receive -> return ReceiveController()
            R.id.navigation_status -> return NodeDetailsController()
            R.id.navigation_settings -> return SettingsController()
            else -> return null
        }
    }
}