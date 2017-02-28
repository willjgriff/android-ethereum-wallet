package com.github.wiljgriff.ethereumwallet.data.ethereum.delegates

import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Accounts
import org.ethereum.geth.Address

/**
 * Created by Will on 07/02/2017.
 */
class AccountManagerDelegate(private val accountManager: AccountManager) {

    fun getAccounts() = AccountsDelegate(accountManager.accounts)
    fun newAccount(password: String) = AccountDelegate(accountManager.newAccount(password))
    fun deleteAccount(account: AccountDelegate?, password: String) = accountManager.deleteAccount(account?.getAccount(), password)
    fun hasAddress(address: AddressDelegate) = accountManager.hasAddress(address.getAddress())
}

class AccountsDelegate(private val accounts: Accounts) {

    fun size() = accounts.size()
    fun get(position: Long) = AccountDelegate(accounts.get(position))
}

class AccountDelegate(private val account: Account) {

    fun getAddress() = AddressDelegate(account.address)
    fun getFile() = account.file
    fun getAccount() = account
}

class AddressDelegate(private val address: Address) {

    fun getHex() = address.hex
    fun getBytes() = address.bytes
    fun getAddress() = address;
}
