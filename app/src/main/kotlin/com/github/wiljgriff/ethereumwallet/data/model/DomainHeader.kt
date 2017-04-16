package com.github.wiljgriff.ethereumwallet.data.model

import org.ethereum.geth.Header

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainHeader(val hashHex: String) {

    companion object Factory {
        fun fromHeader(header: Header): DomainHeader {
            return DomainHeader(header.hash.hex)
        }
    }
}