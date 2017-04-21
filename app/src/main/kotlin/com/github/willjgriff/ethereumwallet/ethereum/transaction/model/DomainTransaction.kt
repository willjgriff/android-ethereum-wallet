package com.github.willjgriff.ethereumwallet.ethereum.transaction.model

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import java.math.BigInteger

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class DomainTransaction(val fromAddress: DomainAddress,
                        val toAddress: DomainAddress,
                        val value: BigInteger,
                        val time: Long)