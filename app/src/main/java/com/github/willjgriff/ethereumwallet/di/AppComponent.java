package com.github.willjgriff.ethereumwallet.di;

import com.github.wiljgriff.ethereumwallet.di.AccountBalanceModule;
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance;
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager;
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode;
import com.github.willjgriff.ethereumwallet.di.modules.AppModule;
import com.github.willjgriff.ethereumwallet.di.modules.WalletAccountModule;
import com.github.willjgriff.ethereumwallet.di.modules.EthereumNodeModule;
import com.github.willjgriff.ethereumwallet.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */
@Singleton
@Component(modules = {AppModule.class,
	WalletAccountModule.class,
	EthereumNodeModule.class,
	AccountBalanceModule.class})
public interface AppComponent {

	EthereumNode provideEthereum();

	WalletAccountManager provideAccountManager();

	AccountBalance provideAccountBalance();

	void inject(MainActivity mainActivity);
}
