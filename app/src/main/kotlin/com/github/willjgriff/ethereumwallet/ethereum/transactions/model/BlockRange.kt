package com.github.willjgriff.ethereumwallet.ethereum.transactions.model

/**
 * Created by williamgriffiths on 21/04/2017.
 */
data class BlockRange(val upperBlock: Long, var lowerBlock: Long) {

    fun rangeExclusive() = upperBlock - lowerBlock + 1
}