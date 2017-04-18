package com.github.willjgriff.ethereumwallet.data.model

import org.ethereum.geth.Header

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainHeader(val hashHex: String, val time: Long) {

    companion object Factory {
        fun fromHeader(header: Header): DomainHeader =
                DomainHeader(header.hash.hex, header.time)
    }
}