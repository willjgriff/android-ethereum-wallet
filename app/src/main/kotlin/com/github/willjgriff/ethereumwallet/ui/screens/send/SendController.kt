package com.github.willjgriff.ethereumwallet.ui.screens.send

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.screens.send.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.send.mvp.SendPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.send.mvp.SendView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.controller_send.view.*
import javax.inject.Inject

/**
 * Created by Will on 29/01/2017.
 */
class SendController : BaseMvpControllerKotlin<SendView, SendPresenter>(),
        SendView {

    override val mvpView: SendView get() = this
    @Inject lateinit override var presenter: SendPresenter

    init {
        injectPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_send, container, false)
        setupToolbarTitle()
        setupObservables(view)
        return view
    }

    private fun setupToolbarTitle() {
        if (targetController is NavigationToolbarListener) {
            (targetController as NavigationToolbarListener)
                    .setToolbarTitle(applicationContext!!.getString(R.string.controller_send_title))
        }
    }

    private fun setupObservables(view: View) {
        val recipientAddress = view.controller_send_recipient_address
        val sendAmount = view.controller_send_amount
        val accountPassword = view.controller_send_password
        val sendEtherClicked = RxView.clicks(view.controller_send_send_ether).share()

        recipientAddress.setCheckValidationTrigger(sendEtherClicked)
        sendAmount.setCheckValidationTrigger(sendEtherClicked)
        accountPassword.setCheckValidationTrigger(sendEtherClicked)

        presenter.apply {
            recipientChanged = recipientAddress.textChangedObservable
            recipientValid = recipientAddress.textValidObservable
            amountChanged = sendAmount.textChangedObservable
            amountValid = sendAmount.textValidObservable
            passwordChanged = accountPassword.textChangedObservable
            passwordValid = accountPassword.textValidObservable
            sendClicked = sendEtherClicked
        }
    }

    override fun displayTransactionSubmitted() {
        activity?.let {
            AlertDialog.Builder(it)
                    .setTitle(applicationContext?.getString(R.string.controller_send_success_dialog_title))
                    .setMessage(applicationContext?.getString(R.string.controller_send_success_dialog_message))
                    .setPositiveButton(applicationContext?.getString(R.string.common_ok), { dialog, _ -> dialog.dismiss() })
                    .show()
        }
    }

    override fun displayErrorSubmittingTx() {
        activity?.let {
            AlertDialog.Builder(it)
                    .setTitle(applicationContext?.getString(R.string.controller_send_error_dialog_title))
                    .setMessage(applicationContext?.getString(R.string.controller_send_error_dialog_message))
                    .setPositiveButton(applicationContext?.getString(R.string.common_ok), { dialog, _ -> dialog.dismiss() })
                    .show()
        }
    }

    override fun setBalance(balance: String) {
        view?.controller_send_current_balance?.text = balance
    }

    override fun setPendingBalance(pendingBalance: String) {
        view?.controller_send_pending_balance?.text = pendingBalance
    }
}
