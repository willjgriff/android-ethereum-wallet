package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountManagerDelegate;

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
public class EthereumModule {

	@Provides
	@Singleton
	@Named("account_position")
	int activeAccountPosition() {
		return 0;
	}

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
	AccountManagerDelegate providesEthereumAccountManagerDelegate(AccountManager accountManager) {
		return new AccountManagerDelegate(accountManager);
	}

	@Provides
	@Singleton
	EthereumAccountManagerKotlin providesEthereumAccountManager(AccountManagerDelegate accountManagerDelegate,
	                                                            @Named("account_position") int activeAccountPosition) {
		return new EthereumAccountManagerKotlin(accountManagerDelegate, activeAccountPosition);
	}
}
