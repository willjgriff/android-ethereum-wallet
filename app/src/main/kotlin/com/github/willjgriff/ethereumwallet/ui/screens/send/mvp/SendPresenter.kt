package com.github.willjgriff.ethereumwallet.ui.screens.send.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AddressBalance
import com.github.willjgriff.ethereumwallet.ethereum.common.model.Denomination
import com.github.willjgriff.ethereumwallet.ethereum.common.model.EtherAmount
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionManager
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.SendTransaction
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenterKotlin
import com.github.willjgriff.ethereumwallet.ui.screens.send.model.WholeTransaction
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SendPresenter @Inject constructor(private val transactionManager: TransactionManager,
                                        private val addressBalance: AddressBalance)
    : BaseMvpPresenterKotlin<SendView>() {

    private var SEND_SPAM_PREVENTION_DELAY_SECONDS = 2L

    lateinit var recipientChanged: Observable<String>
    lateinit var recipientValid: Observable<Boolean>
    lateinit var amountChanged: Observable<String>
    lateinit var amountValid: Observable<Boolean>
    lateinit var passwordChanged: Observable<String>
    lateinit var passwordValid: Observable<Boolean>
    lateinit var sendClicked: Observable<Any>

    override fun viewReady() {
        setBalance()
        setupSendClicked()
    }

    private fun setBalance() {
        addDisposable(addressBalance.getBalanceAtActiveAddress()
                .subscribe { view?.setBalance(it) })

        addDisposable(addressBalance.getPendingBalanceAtActiveAddress()
                .subscribe { view?.setPendingBalance(it) })
    }

    private fun setupSendClicked() {
        sendClicked.subscribe { _ -> view.toString() }

        val allInputValid = Observable.combineLatest(recipientValid, amountValid, passwordValid,
                Function3<Boolean, Boolean, Boolean, Boolean> { recipientValid, amountValid, passwordValid -> recipientValid && amountValid && passwordValid })
                .distinctUntilChanged()

        sendClicked
                .withLatestFrom(allInputValid, BiFunction<Any, Boolean, Any> { _, validInput -> validInput })
                .filter { it == true }
                // This is to prevent spamming
                .throttleFirst(SEND_SPAM_PREVENTION_DELAY_SECONDS, TimeUnit.SECONDS)
                .flatMap { packageTransactionFromInput() }
                .subscribe { submitTransaction(it) }
    }

    fun packageTransactionFromInput(): Observable<WholeTransaction>? {
        return Observable.zip<String, EtherAmount, SendTransaction>(recipientChanged, mapAmountToEtherAmount(),
                BiFunction { recipient, amount -> SendTransaction(recipient, amount) })
                .zipWith(passwordChanged, BiFunction<SendTransaction, String, WholeTransaction>(::WholeTransaction))
    }

    fun mapAmountToEtherAmount(): Observable<EtherAmount> = amountChanged
            .map { EtherAmount(BigDecimal(it), Denomination.ETHER) }

    private fun submitTransaction(it: WholeTransaction) {
        val txSubmittedSuccessfully = transactionManager.executeTransaction(it.sendTransaction, it.password)
        if (txSubmittedSuccessfully) {
            view?.displayTransactionSubmitted()
        } else {
            view?.displayErrorSubmittingTx()
        }
    }
}