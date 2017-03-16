package com.github.wiljgriff.ethereumwallet.ethereum.payment

/**
 * Created by Will on 12/03/2017.
 */
data class SendPayment(val hexAddress: String = "", val amount: Double = 0.0, val label: String = "")