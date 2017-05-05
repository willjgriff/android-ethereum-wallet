package com.github.willjgriff.ethereumwallet.ui.screens.createaccount.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class CreateAccountPresenter @Inject constructor(private val addressManager: AddressManager) : BaseMvpPresenterKotlin<CreateAccountView>() {

    lateinit var passwordChanged: Observable<String>
    lateinit var passwordValid: Observable<Boolean>
    lateinit var submitClicked: Observable<Any>

    override fun viewReady() {
        setupSubmitObservable()
    }

    private fun setupSubmitObservable() {
        // For adding a confirm passwordChanged field.
        //		Observable<Boolean> validCombined = Observable
        //			.combineLatest(mValidMessage, mValidPhone, mValidEmail,
        //				(messageValid, phoneValid, emailValid) -> messageValid && phoneValid && emailValid)
        //			.distinctUntilChanged();

        submitClicked
                .withLatestFrom(passwordValid, BiFunction<Any, Boolean, Any> { _, validPassword -> validPassword })
                .filter { it == true }
                .flatMap { passwordChanged }
                // This is to prevent spamming and creating many accounts
                .first("")
                .subscribe { password -> setupWallet(password) }
    }

    private fun setupWallet(password: String) {
        addressManager.createActiveAddress(password)
        view?.openWallet()
    }
}