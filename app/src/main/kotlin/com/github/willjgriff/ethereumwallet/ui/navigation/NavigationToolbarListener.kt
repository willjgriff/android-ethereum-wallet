package com.github.willjgriff.ethereumwallet.ui.navigation

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface NavigationToolbarListener {

    fun setToolbarTitle(toolbarTitle: CharSequence)

    fun setBalance(balance: String)
}
