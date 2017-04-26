package com.github.willjgriff.ethereumwallet.ethereum.node.model

import org.ethereum.geth.Header

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainBlockHeader(val hashHex: String,
                             val time: Long,
                             val blockNumber: Long) {

    companion object Factory {
        fun fromHeader(header: Header): DomainBlockHeader =
                DomainBlockHeader(header.hash.hex, header.time, header.number)
    }
}