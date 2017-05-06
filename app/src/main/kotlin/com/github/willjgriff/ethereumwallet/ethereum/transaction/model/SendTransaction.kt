package com.github.willjgriff.ethereumwallet.ethereum.transaction.model

import com.github.willjgriff.ethereumwallet.ethereum.common.model.Denomination
import com.github.willjgriff.ethereumwallet.ethereum.common.model.EtherAmount
import java.math.BigDecimal

/**
 * Created by Will on 12/03/2017.
 */
data class SendTransaction(val toAddress: String = "",
                           val etherAmount: EtherAmount = EtherAmount(BigDecimal("0"), Denomination.WEI),
                           val label: String = "")