package com.github.willjgriff.ethereumwallet.di;

import org.ethereum.geth.EthereumClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */
@Component(modules = {AppModule.class, EthereumModule.class})
@Singleton
public interface AppComponent {

	EthereumClient provideEthereumClient();
}
