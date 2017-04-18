package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAddress
import org.ethereum.geth.Account
import org.ethereum.geth.Accounts

/**
 * Created by williamgriffiths on 18/04/2017.
 */
fun List<Account>.getGethAccountFromAddress(address: DomainAddress): Account? =
        filter { it.address.hex == address.hex }
                .singleOrNull()

fun Accounts.asList(): List<Account> =
        0L.rangeTo(size() - 1)
                .map { get(it) }