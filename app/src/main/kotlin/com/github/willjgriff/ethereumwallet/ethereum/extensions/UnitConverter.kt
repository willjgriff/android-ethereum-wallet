package com.github.willjgriff.ethereumwallet.ethereum.extensions

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by williamgriffiths on 20/04/2017.
 */
fun BigInteger.fromWeiTo(denomination: Denomination): BigDecimal =
        BigDecimal(toString())
                .divide(BigDecimal(denomination.numberOfWei))

enum class Denomination(val numberOfWei: Long) {
    WEI(1),
    KWEI(1000),
    MWEI(1000000),
    SHANNON(1000000000), GWEI(1000000000),
    SZABO(1000000000000),
    FINNEY(1000000000000000),
    ETHER(1000000000000000000)
}