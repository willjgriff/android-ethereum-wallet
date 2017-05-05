package com.github.willjgriff.ethereumwallet.ethereum.common.model

import java.math.BigDecimal

/**
 * Created by williamgriffiths on 22/04/2017.
 */
data class EtherAmount(val amount: BigDecimal, val denomination: Denomination) {

    fun to(targetDenomination: Denomination): EtherAmount {

        val amountInWei: BigDecimal = amount.multiply(BigDecimal(this.denomination.numberOfWei))
        var amountInTargetDenomination: BigDecimal = amountInWei.divide(BigDecimal(targetDenomination.numberOfWei))

        if (isWholeNumber(amountInTargetDenomination)) {
            amountInTargetDenomination = amountInTargetDenomination.setScale(0)
        }

        return EtherAmount(amountInTargetDenomination, targetDenomination)
    }

    fun isWholeNumber(number: BigDecimal): Boolean {
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
    }

}