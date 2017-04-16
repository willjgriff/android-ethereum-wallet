package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge;
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress;
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager;

import org.ethereum.geth.Geth;
import org.ethereum.geth.KeyStore;

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
	@Named("key_store_location")
	String providesKeyStoreLocation(Context context) {
		return context.getFilesDir().toString() + "/key_store_location/";
	}

	@Provides
	@Singleton
	KeyStore providesKeyStore(@Named("key_store_location") String keyStoreLocation) {
		return new KeyStore(keyStoreLocation, Geth.LightScryptN, Geth.LightScryptP);
	}
	
	@Provides
	@Singleton
	AccountsBridge providesAccountsBridge(KeyStore keyStore) {
		return new AccountsBridge(keyStore);
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
