package com.github.willjgriff.ethereumwallet.di;

import android.content.Context;

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumKeystoreLocation;
import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumManager;

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
	EthereumKeystoreLocation providesKeystoreLocation(Context context) {
		return new EthereumKeystoreLocation(context);
	}

	@Provides
	@Singleton
	EthereumManager providesEthereum(EthereumKeystoreLocation ethereumKeystoreLocation) {
		return new EthereumManager(ethereumKeystoreLocation);
	}
}
