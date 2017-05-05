package com.github.willjgriff.ethereumwallet.ethereum.common.model

/**
 * Created by williamgriffiths on 05/05/2017.
 */
enum class Denomination(val numberOfWei: Long) {
    WEI(1),
    KWEI(1000),
    MWEI(1000000),
    SHANNON(1000000000), GWEI(1000000000),
    SZABO(1000000000000),
    FINNEY(1000000000000000),
    ETHER(1000000000000000000)
}