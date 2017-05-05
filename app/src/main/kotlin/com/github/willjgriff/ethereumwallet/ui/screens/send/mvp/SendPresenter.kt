package com.github.willjgriff.ethereumwallet.ui.screens.send.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AddressBalance
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionManager
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.SendTransaction
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import com.github.willjgriff.ethereumwallet.ui.screens.send.model.WholeTransaction
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SendPresenter @Inject constructor(private val transactionManager: TransactionManager,
                                        private val addressBalance: AddressBalance)
    : BaseMvpPresenterKotlin<SendView>() {

    lateinit var recipientChanged: Observable<String>
    lateinit var recipientValid: Observable<Boolean>
    lateinit var amountChanged: Observable<String>
    lateinit var amountValid: Observable<Boolean>
    lateinit var passwordChanged: Observable<String>
    lateinit var passwordValid: Observable<Boolean>
    lateinit var sendClicked: Observable<Any>

    override fun viewReady() {
        setBalance()

        sendClicked.subscribe { _ -> view.toString() }

        val allInputValid = Observable.combineLatest(recipientValid, amountValid, passwordValid,
                Function3<Boolean, Boolean, Boolean, Boolean> { recipientValid, amountValid, passwordValid -> recipientValid && amountValid && passwordValid })
                .distinctUntilChanged()

        sendClicked
                .withLatestFrom(allInputValid, BiFunction<Any, Boolean, Any> { _, validInput -> validInput })
                .filter { it == true }
                // This is to prevent spamming
                .first(true)
                .toObservable()
                .flatMap { packageTransactionFromInput() }
                .subscribe { submitTransaction(it) }
    }

    private fun setBalance() {
        addDisposable(addressBalance.getBalanceAtActiveAddress()
                .subscribe { view?.setBalance(it) })

        addDisposable(addressBalance.getPendingBalanceAtActiveAddress()
                .subscribe { view?.setPendingBalance(it) })
    }

    fun packageTransactionFromInput(): Observable<WholeTransaction>? {
        return Observable.zip<String, String, SendTransaction>(recipientChanged, amountChanged,
                BiFunction { recipient, amount -> SendTransaction(recipient, amount.toLong()) })
                .zipWith(passwordChanged, BiFunction<SendTransaction, String, WholeTransaction>(::WholeTransaction))
    }

    private fun submitTransaction(it: WholeTransaction) {
        transactionManager.executeTransaction(it.sendTransaction, it.password)
        view?.displayTransactionSubmitted()
    }
}