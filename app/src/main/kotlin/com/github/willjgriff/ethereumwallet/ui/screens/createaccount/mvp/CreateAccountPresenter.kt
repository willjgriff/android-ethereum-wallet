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

    private lateinit var password: Observable<String>
    private lateinit var validPassword: Observable<Boolean>
    private lateinit var submitButtonShare: Observable<Any>

    fun setObservables(passwordObservable: Observable<String>,
                       passwordValid: Observable<Boolean>,
                       submitButtonObservable: Observable<Any>) {
        password = passwordObservable
        validPassword = passwordValid
        submitButtonShare = submitButtonObservable
    }

    override fun viewReady() {
        setupSubmitObservable()
    }

    private fun setupSubmitObservable() {
        // For adding a confirm password field.
        //		Observable<Boolean> validCombined = Observable
        //			.combineLatest(mValidMessage, mValidPhone, mValidEmail,
        //				(messageValid, phoneValid, emailValid) -> messageValid && phoneValid && emailValid)
        //			.distinctUntilChanged();

        submitButtonShare
                .withLatestFrom(validPassword, BiFunction<Any, Boolean, Any> { _, validPassword -> validPassword })
                .filter { it == true }
                .flatMap { password }
                // This is to prevent spamming and creating many accounts
                .first("")
                .subscribe { password ->
                    addressManager.createActiveAddress(password.toString())
                    view?.openWallet()
                }
    }
}