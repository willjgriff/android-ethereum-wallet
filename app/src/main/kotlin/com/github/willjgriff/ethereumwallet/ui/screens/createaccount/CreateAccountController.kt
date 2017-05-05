package com.github.willjgriff.ethereumwallet.ui.screens.createaccount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.mvp.CreateAccountPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.createaccount.mvp.CreateAccountView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.controller_create_account.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class CreateAccountController : BaseMvpControllerKotlin<CreateAccountView, CreateAccountPresenter>(), CreateAccountView {

    override val mvpView: CreateAccountView
        get() = this
    @Inject lateinit override var presenter: CreateAccountPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        injectPresenter()
        val view = inflater.inflate(R.layout.controller_create_account, container, false)
        setPresenterObservables(view)
        return view
    }

    private fun setPresenterObservables(view: View) {
        val passwordField = view.controller_create_account_password_text_input

        val passwordChanged = passwordField.textChangedObservable
        val passwordValid = passwordField.textValidObservable
        val submitClicked = RxView.clicks(view.controller_create_account_create_button).share()
        passwordField.setCheckValidationTrigger(submitClicked)

        presenter.apply {
            this.passwordChanged = passwordChanged
            this.passwordValid = passwordValid
            this.submitClicked = submitClicked
        }
    }

    override fun openWallet() {
        if (targetController is CreateAccountCompletedListener) {
            (targetController as CreateAccountCompletedListener).onAccountCreated()
        }
    }
}