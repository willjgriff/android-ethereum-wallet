package com.github.willjgriff.ethereumwallet.di;

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumManager;
import com.github.willjgriff.ethereumwallet.ui.MainActivity;

import org.ethereum.geth.EthereumClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */
@Singleton
@Component(modules = {AppModule.class, EthereumModule.class})
public interface AppComponent {

	EthereumManager provideEthereumManager();

	void inject(MainActivity mainActivity);
}
