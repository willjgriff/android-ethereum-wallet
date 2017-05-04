package com.github.willjgriff.ethereumwallet.ui.screens.send.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SendPresenter @Inject constructor(private val addressManager: AddressManager) : BaseMvpPresenterKotlin<SendView>() {

    private var sendEther: Observable<Any>? = null

    override fun viewReady() {}

    fun setObservables(recipientAddressObservable: Observable<CharSequence>,
                       sendAmountObservable: Observable<CharSequence>,
                       accountPasswordObservable: Observable<CharSequence>,
                       sendObservable: Observable<Any>) {

        sendEther = sendObservable
        sendEther?.subscribe { _ -> view.toString() }
    }

    override fun releaseViewReferences() {
        sendEther = null
    }
}