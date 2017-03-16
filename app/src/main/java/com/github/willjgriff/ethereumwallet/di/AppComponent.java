package com.github.willjgriff.ethereumwallet.di;

import com.github.wiljgriff.ethereumwallet.ethereum.account.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.di.modules.AppModule;
import com.github.willjgriff.ethereumwallet.di.modules.EthereumAccountModule;
import com.github.willjgriff.ethereumwallet.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */
@Singleton
@Component(modules = {AppModule.class, EthereumAccountModule.class})
public interface AppComponent {

	EthereumAccountManagerKotlin provideEthereumManager();

	void inject(MainActivity mainActivity);
}
