package com.github.willjgriff.ethereumwallet.ethereum.address.model

import org.ethereum.geth.Address

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainAddress(val hex: String = "NO_ADDRESS_HEX") {

    companion object Factory {
        fun fromAddress(address: Address): DomainAddress =
                DomainAddress(address.hex)
    }
}