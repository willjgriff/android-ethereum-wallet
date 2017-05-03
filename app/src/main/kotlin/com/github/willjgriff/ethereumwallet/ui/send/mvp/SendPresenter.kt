package com.github.willjgriff.ethereumwallet.ui.send.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SendPresenter @Inject constructor(private val mAddressManager: AddressManager) : BaseMvpPresenter<SendView>() {

    private var mSendEther: Observable<Any>? = null

    override fun viewReady() {}

    fun setObservables(recipientAddressObservable: Observable<CharSequence>,
                       sendAmountObservable: Observable<CharSequence>,
                       accountPasswordObservable: Observable<CharSequence>,
                       sendObservable: Observable<Any>) {

        mSendEther = sendObservable
        mSendEther?.subscribe { _ -> view.toString() }
    }

    override fun releaseViewReferences() {
        mSendEther = null
    }
}