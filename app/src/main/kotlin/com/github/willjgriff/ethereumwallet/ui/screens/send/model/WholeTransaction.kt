package com.github.willjgriff.ethereumwallet.ui.screens.send.model

import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.SendTransaction

/**
 * Created by williamgriffiths on 05/05/2017.
 */
data class WholeTransaction(val sendTransaction: SendTransaction, val password: String)