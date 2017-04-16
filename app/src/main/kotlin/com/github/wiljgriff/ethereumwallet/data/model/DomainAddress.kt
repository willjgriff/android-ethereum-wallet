package com.github.wiljgriff.ethereumwallet.data.model

import org.ethereum.geth.Address

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainAddress(val hex: String = "",
                         val bytes: ByteArray = ByteArray(0)) {

    companion object Factory {
        fun fromAddress(address: Address): DomainAddress =
                DomainAddress(address.hex, address.bytes)
    }
}