package com.github.wiljgriff.ethereumwallet.data.model

import org.ethereum.geth.Account

/**
 * Created by williamgriffiths on 16/04/2017.
 */
data class DomainAccount(val address: DomainAddress = DomainAddress(),
                         val file: String = "") {

    companion object Factory {
        fun fromAccount(account: Account) =
                DomainAccount(DomainAddress.fromAddress(account.address), account.file)
    }
}