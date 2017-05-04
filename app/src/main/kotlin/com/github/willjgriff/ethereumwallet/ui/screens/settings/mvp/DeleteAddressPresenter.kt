package com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by Will on 04/05/2017.
 *
 *
 * Note this uses AutoFactory which means it can't be retained beyond the life of
 * the Controller / AlertDialog. Therefore we don't have to release references to
 * View containing objects or dispose of Observable Subscriptions.
 */
class DeleteAddressPresenter @Inject constructor(private val addressManager: AddressManager)
    : BaseMvpPresenterKotlin<DeleteAddressView>() {

    private lateinit var deleteClick: Observable<Any>
    private lateinit var cancelClick: Observable<Any>
    private lateinit var passwordValid: Observable<Boolean>
    private lateinit var passwordChanged: Observable<String>

    fun setObservables(deleteClick: Observable<Any>, cancelClick: Observable<Any>,
                       passwordValid: Observable<Boolean>, passwordChanged: Observable<String>) {
        this.deleteClick = deleteClick
        this.cancelClick = cancelClick
        this.passwordValid = passwordValid
        this.passwordChanged = passwordChanged
    }

    override fun viewReady() {
        setupDeleteButton(deleteClick, passwordValid, passwordChanged)

        cancelClick.subscribe { _ -> view?.closeDialog() }
    }

    private fun setupDeleteButton(deleteButton: Observable<Any>, passwordValid: Observable<Boolean>, passwordChanged: Observable<String>) {
        val enteredPasswordObservable = passwordChanged
                .replay(1)
                .autoConnect()

        val deleteObservable = deleteButton
                .withLatestFrom<Boolean, Boolean>(passwordValid, BiFunction<Any, Boolean, Boolean> { _, passwordIsValid -> passwordIsValid })
                .filter { passwordIsValid -> passwordIsValid }
                .flatMap { _ -> enteredPasswordObservable }
                .map { validPassword -> addressManager.deleteActiveAddress(validPassword) }
                .share()

        // Show incorrect password error if the password is incorrect
        deleteObservable
                .filter({ deleteAccount -> !deleteAccount })
                .subscribe { _ -> incorrectPasswordEntered() }

        // Delete active account if the password is correct
        deleteObservable
                .filter({ deleteAccount -> deleteAccount })
                .subscribe { _ -> deleteAccount() }
    }

    private fun incorrectPasswordEntered() {
        view?.incorrectPasswordEntered()
        view?.closeDialog()
    }

    private fun deleteAccount() {
        view?.addressDeleted()
        view?.closeDialog()
    }
}