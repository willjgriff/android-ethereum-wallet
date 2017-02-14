package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

@AutoFactory
public class SettingsPresenter extends BaseMvpPresenter<SettingsView> {

	public SettingsPresenter(@Provided EthereumAccountManagerKotlin ethereumAccountManagerKotlin,
	                         Observable<Object> newAccountButton) {

		newAccountButton.subscribe(clicked -> getView().openCreateAccountScreen());
	}

	@Override
	public void viewReady() {

	}
}
