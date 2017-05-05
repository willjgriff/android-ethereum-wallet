package com.github.willjgriff.ethereumwallet.ethereum.common

import com.github.willjgriff.ethereumwallet.ethereum.common.model.Denomination
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by williamgriffiths on 20/04/2017.
 *
 * See for unit conversion:
 * https://ethereum.stackexchange.com/questions/253/the-ether-denominations-are-called-finney-szabo-and-wei-what-who-are-these-na
 */
fun BigInteger.fromWeiTo(denomination: Denomination): BigDecimal =
        BigDecimal(toString())
                .divide(BigDecimal(denomination.numberOfWei))

fun BigDecimal.fromDenominationToWei(denomination: Denomination): BigInteger =
        multiply(BigDecimal(denomination.numberOfWei))
                .toBigInteger()