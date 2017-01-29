package com.github.willjgriff.ethereumwallet.data.ethereum;

import android.support.annotation.Nullable;

import org.ethereum.geth.Account;
import org.ethereum.geth.AccountManager;
import org.ethereum.geth.Accounts;
import org.ethereum.geth.Geth;

import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class EthereumManager {

	private EthereumKeystoreLocation mEthereumKeystoreLocation;

	public EthereumManager(EthereumKeystoreLocation ethereumKeystoreLocation) {
		mEthereumKeystoreLocation = ethereumKeystoreLocation;
	}

	public void sendEther(String address, long amount) {

	}

	// TODO: This could put a user into a deadlock, not capable of creating or deleting accounts, if they forget there password.
	public void deleteAccount(String password) {
		AccountManager accountManager = new AccountManager(mEthereumKeystoreLocation.getLocation(), Geth.LightScryptN, Geth.LightScryptP);
		Accounts accounts = accountManager.getAccounts();
		try {
			accountManager.deleteAccount(accounts.get(0), password);
		} catch (Exception e) {
			Timber.e(e, "Error deleting Ethereum account");
		}
	}

	public void createAccount(String password) {
		AccountManager accountManager = new AccountManager(mEthereumKeystoreLocation.getLocation(), Geth.LightScryptN, Geth.LightScryptP);
		try {
			if (accountManager.getAccounts().size() <= 0) {
				Account ethereumAccount = accountManager.newAccount(password);
			} else {
				Timber.e("Error creating Ethereum account: already got one");
			}
		} catch (Exception e) {
			Timber.e(e, "Error creating Ethereum account");
		}
	}

	@Nullable
	public Account getAccount() {
		AccountManager accountManager = new AccountManager(mEthereumKeystoreLocation.getLocation(), Geth.LightScryptN, Geth.LightScryptP);
		Account currentAccount = null;
		try {
			if (accountManager.getAccounts().size() > 0 && accountManager.getAccounts().get(0) != null) {
				currentAccount = accountManager.getAccounts().get(0);
			}
		} catch (Exception e) {
			Timber.e(e, "Error getting account");
		}

		return currentAccount;
	}

}
