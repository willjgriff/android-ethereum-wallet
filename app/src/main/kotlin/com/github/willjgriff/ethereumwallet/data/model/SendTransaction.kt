package com.github.willjgriff.ethereumwallet.data.model

/**
 * Created by Will on 12/03/2017.
 */
data class SendTransaction(val toAddress: String = "",
                           val amount: Long = 0L,
                           val label: String = "")