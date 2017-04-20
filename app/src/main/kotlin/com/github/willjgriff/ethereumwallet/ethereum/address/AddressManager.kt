package com.github.willjgriff.ethereumwallet.ethereum.address

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class AddressManager(private val addressAdapter: AddressAdapter,
                     private val activeAddress: ActiveAddress) {

    fun createActiveAddress(password: String) {
        val newAddress = addressAdapter.newAddress(password)
        activeAddress.setHex(newAddress.hex)
    }

    fun getActiveAddress(): DomainAddress = getAllAddresses()
            .filter { it.hex == activeAddress.getHex() }
            .singleOrNull() ?: DomainAddress()

    fun hasAddress() = getAllAddresses().isNotEmpty()

    fun getAllAddresses(): List<DomainAddress> = addressAdapter.getAddresses()

    fun deleteActiveAddress(password: String): Boolean {
        try {
            if (activeAddress.hasActiveAddress()) {
                addressAdapter.deleteAddress(getActiveAddress(), password)
                activeAddress.deleteActiveAddress()
            }
        } catch (exception: Exception) {
            Timber.i(exception, "Account not deleted, password probably incorrect")
        }

        return !activeAddress.hasActiveAddress()
    }

    fun setActiveAccount(address: DomainAddress) {
        activeAddress.setHex(address.hex)
    }

}