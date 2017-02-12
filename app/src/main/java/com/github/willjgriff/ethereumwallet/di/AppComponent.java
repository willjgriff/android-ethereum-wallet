package com.github.willjgriff.ethereumwallet.di;

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumAccountManager;
import com.github.willjgriff.ethereumwallet.di.modules.AppModule;
import com.github.willjgriff.ethereumwallet.di.modules.EthereumModule;
import com.github.willjgriff.ethereumwallet.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */
@Singleton
@Component(modules = {AppModule.class, EthereumModule.class})
public interface AppComponent {

	EthereumAccountManager provideEthereumManager();

	void inject(MainActivity mainActivity);
}
