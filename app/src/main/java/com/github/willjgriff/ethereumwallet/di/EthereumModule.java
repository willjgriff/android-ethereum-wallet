package com.github.willjgriff.ethereumwallet.di;

import org.ethereum.geth.EthereumClient;

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
	EthereumClient providesEthereum() {
		return new EthereumClient("Some string");
	}
}
