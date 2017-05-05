package com.github.willjgriff.ethereumwallet.ethereum.transaction.model

/**
 * Created by Will on 12/03/2017.
 */
data class SendTransaction(val toAddress: String = "",
                           val amountInWei: Long = 0L,
                           val label: String = "")