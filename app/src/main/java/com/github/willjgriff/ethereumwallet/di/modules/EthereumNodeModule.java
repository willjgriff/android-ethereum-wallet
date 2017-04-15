package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;

import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 16/03/2017.
 */

@Module
public class EthereumNodeModule {

	@Provides
	@Singleton
	@Named("ethereum_path")
	String provideEthereumPath(Context context) {
		return context.getFilesDir().toString() + "/ethereum/";
	}

	@Provides
	@Singleton
	EthereumNode provideEthereum(@Named("ethereum_path") String ethereumPath) {
		return new EthereumNode(ethereumPath);
	}
}
