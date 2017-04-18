package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAddress
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class AddressManager(private val addressAdapter: AddressAdapter,
                     private val activeAccountAddress: ActiveAccountAddress) {

    fun createActiveAddress(password: String) {
        val newAddress = addressAdapter.newAddress(password)
        activeAccountAddress.set(newAddress.hex)
    }

    fun getActiveAddress(): DomainAddress = getAllAddresses()
            .filter { it.hex == activeAccountAddress.get() }
            .singleOrNull() ?: DomainAddress()

    fun hasAddress() = getAllAddresses().isNotEmpty()

    fun getAllAddresses(): List<DomainAddress> = addressAdapter.getAddresses()

    fun deleteActiveAddress(password: String): Boolean {
        try {
            if (activeAccountAddress.hasActiveAddress()) {
                addressAdapter.deleteAddress(getActiveAddress(), password)
                activeAccountAddress.deleteActiveAddress()
            }
        } catch (exception: Exception) {
            Timber.i(exception, "Account not deleted, password probably incorrect")
        }

        return !activeAccountAddress.hasActiveAddress()
    }

    fun setActiveAccount(address: DomainAddress) {
        activeAccountAddress.set(address.hex)
    }

}