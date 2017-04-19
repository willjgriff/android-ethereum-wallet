package com.github.willjgriff.ethereumwallet.ethereum.address.balance

import org.ethereum.geth.Address
import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import java.math.BigInteger

/**
 * Created by williamgriffiths on 17/04/2017.
 */
class AddressBalanceAdapter(private val ethereumClient: EthereumClient,
                            private val context: Context) {

    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L

    fun getBalanceAt(addressString: String): BigInteger = try {
        val bigIntBalance = ethereumClient.getBalanceAt(context, Address(addressString), DEFAULT_TO_CURRENT_BLOCK_VALUE)
        BigInteger(bigIntBalance.string())
    } catch (exception: Exception) {
        BigInteger("0")
    }

    fun getPendingBalanceAt(addressString: String): BigInteger = try {
        val bigIntBalance = ethereumClient.getPendingBalanceAt(context, Address(addressString))
        BigInteger(bigIntBalance.string())
    } catch (exception: Exception) {
        BigInteger("0")
    }

}