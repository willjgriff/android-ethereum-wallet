package com.github.wiljgriff.ethereumwallet.ethereum.account.balance

import org.ethereum.geth.Address
import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import java.math.BigInteger

/**
 * Created by williamgriffiths on 17/04/2017.
 */
class AccountBalanceAdapter(private val ethereumClient: EthereumClient,
                            private val context: Context) {

    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L

    fun getBalanceAt(addressString: String): BigInteger = try {
        BigInteger(ethereumClient.getBalanceAt(context, Address(addressString), DEFAULT_TO_CURRENT_BLOCK_VALUE).string())
    } catch (exception: Exception) {
        BigInteger("0")
    }

    fun getPendingBalanceAt(addressString: String): BigInteger = try {
        BigInteger(ethereumClient.getPendingBalanceAt(context, Address(addressString)).string())
    } catch (exception: Exception) {
        BigInteger("0")
    }

}