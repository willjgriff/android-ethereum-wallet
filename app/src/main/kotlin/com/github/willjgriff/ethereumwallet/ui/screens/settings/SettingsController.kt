package com.github.willjgriff.ethereumwallet.ui.screens.settings

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.SettingsCreateAccountController
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.SettingsCreateAccountController.SettingsToolbarListener
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.screens.settings.DeleteAddressAlertDialog.SettingsDeleteAlertDialogListener
import com.github.willjgriff.ethereumwallet.ui.screens.settings.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp.SettingsPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp.SettingsView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.controller_settings.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class SettingsController : BaseMvpControllerKotlin<SettingsView, SettingsPresenter>(),
        SettingsView, SettingsDeleteAlertDialogListener, SettingsToolbarListener {

    override val mvpView: SettingsView
        get() = this
    @Inject override lateinit var presenter: SettingsPresenter

    private lateinit var activeAddress: TextView

    init {
        injectPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_settings, container, false)
        activeAddress = view.controller_settings_current_address
        setObservablesOnPresenter(view)
        setToolbarTitle(applicationContext?.getString(R.string.controller_settings_title) ?: "")
        return view
    }

    private fun setObservablesOnPresenter(view: View) {
        val newAddressClick = RxView.clicks(view.controller_settings_new_address)
        val changeAddressClick = RxView.clicks(view.controller_settings_change_address)
        val deleteAddressClick = RxView.clicks(view.controller_settings_delete_address)
        presenter.setObservables(newAddressClick, changeAddressClick, deleteAddressClick)
    }

    override fun setToolbarTitle(title: CharSequence) {
        if (targetController is NavigationToolbarListener) {
            (targetController as NavigationToolbarListener).setToolbarTitle(title)
        }
    }

    override fun openCreateAccountScreen() {
        val settingsCreateAccountController = SettingsCreateAccountController()
        settingsCreateAccountController.targetController = this
        router.pushController(RouterTransaction.with(settingsCreateAccountController)
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }

    override fun showDeleteAddressDialog() {
        DeleteAddressAlertDialog(activity!!, this).show()
    }

    override fun setActiveAddress(address: String) {
        activeAddress.text = address
    }

    override fun setAddressDeleted() {
        activeAddress.setText(R.string.controller_settings_address_deleted)
    }

    override fun openChangeAddressScreen() {
        router.pushController(RouterTransaction.with(ChangeAddressController())
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler()))
    }

    override fun showIncorrectPasswordDialog() {
        AlertDialog.Builder(activity!!)
                .setTitle(R.string.controller_settings_incorrect_password)
                .setMessage(R.string.controller_settings_incorrect_password_message)
                .setPositiveButton(R.string.common_ok) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    override fun addressSuccessfullyDeleted() {
        activeAddress.setText(R.string.controller_settings_address_deleted)
    }
}