package com.github.willjgriff.ethereumwallet.ethereum.address

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import org.ethereum.geth.Account
import org.ethereum.geth.Accounts

/**
 * Created by williamgriffiths on 18/04/2017.
 */
fun List<Account>.getGethAccountFromAddress(address: DomainAddress): Account =
        filter { it.address.hex == address.hex }
                .singleOrNull() ?: Account()

fun Accounts.asList(): List<Account> =
        0L.rangeTo(size() - 1)
                .map { get(it) }