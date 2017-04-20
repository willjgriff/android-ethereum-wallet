package com.github.willjgriff.ethereumwallet.ethereum.address

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import org.ethereum.geth.KeyStore


/**
 * Created by williamgriffiths on 16/04/2017.
 */
class AddressAdapter(val keyStore: KeyStore) {

    fun newAddress(password: String): DomainAddress {
        val newAccount = keyStore.newAccount(password)
        return DomainAddress.fromAddress(newAccount.address)
    }

    fun getAddresses(): List<DomainAddress> = keyStore.accounts
            .asList()
            .map { DomainAddress.fromAddress(it.address) }

    fun deleteAddress(activeAddress: DomainAddress, password: String) {
        val gethAccount = keyStore.accounts
                .asList()
                .getGethAccountFromAddress(activeAddress)
        keyStore.deleteAccount(gethAccount, password)
    }
}