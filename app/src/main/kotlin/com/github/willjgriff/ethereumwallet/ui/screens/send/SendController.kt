package com.github.willjgriff.ethereumwallet.ui.screens.send

import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.screens.send.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.send.mvp.SendPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.send.mvp.SendView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.controller_send.view.*
import javax.inject.Inject

/**
 * Created by Will on 29/01/2017.
 */
class SendController : BaseMvpControllerKotlin<SendView, SendPresenter>(),
        SendView {

    override val mvpView: SendView
        get() = this
    @Inject lateinit override var presenter: SendPresenter

    private lateinit var recipientAddress: TextInputLayout
    private lateinit var sendAmount: TextInputLayout
    private lateinit var accountPassword: TextInputLayout
    private lateinit var sendEtherButton: Button

    init {
        injectPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_send, container, false)
        bindViews(view)
        setupToolbarTitle()
        setInputObservables()
        return view
    }

    private fun bindViews(view: View) {
        recipientAddress = view.controller_send_recipient_address
        sendAmount = view.controller_send_amount
        accountPassword = view.controller_send_password
        sendEtherButton = view.controller_send_send_ether
    }

    private fun setupToolbarTitle() {
        if (targetController is NavigationToolbarListener) {
            (targetController as NavigationToolbarListener)
                    .setToolbarTitle(applicationContext!!.getString(R.string.controller_send_title))
        }
    }

    private fun setInputObservables() {
        val recipientAddressObservable = RxTextView.textChanges(recipientAddress.editText!!)
        val sendAmountObservable = RxTextView.textChanges(sendAmount.editText!!)
        val accountPasswordObservable = RxTextView.textChanges(accountPassword.editText!!)
        val sendObservable = RxView.clicks(sendEtherButton)
        presenter.setObservables(recipientAddressObservable, sendAmountObservable, accountPasswordObservable, sendObservable)
    }
}
