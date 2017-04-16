package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge;
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress;
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager;

import org.ethereum.geth.AccountManager;
import org.ethereum.geth.Geth;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 29/01/2017.
 */

@Module
public class WalletAccountModule {

	@Provides
	@Singleton
	@Named("keystore_location")
	String keystoreLocation(Context context) {
		return context.getFilesDir().toString() + "/keystore_location/";
	}

	@Provides
	@Singleton
	AccountManager providesAccountManager(@Named("keystore_location") String keystoreLocation) {
		return new AccountManager(keystoreLocation, Geth.LightScryptN, Geth.LightScryptP);
	}
	
	@Provides
	@Singleton
	AccountsBridge providesAccountsBridge(AccountManager accountManager) {
		return new AccountsBridge(accountManager);
	}

	@Provides
	@Singleton
	ActiveAccountAddress providesActiveAccountAddress(SharedPreferences sharedPreferences) {
		return new ActiveAccountAddress(sharedPreferences);
	}

	@Provides
	@Singleton
	WalletAccountManager providesEthereumAccountManager(AccountsBridge accountsBridge,
	                                                            ActiveAccountAddress activeAccountAddress) {
		return new WalletAccountManager(accountsBridge, activeAccountAddress);
	}
}
