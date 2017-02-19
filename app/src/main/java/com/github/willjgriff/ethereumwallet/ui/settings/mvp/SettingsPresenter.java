package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import android.text.Editable;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

@ControllerScope
public class SettingsPresenter extends BaseMvpPresenter<SettingsView> {

	@Inject
	public SettingsPresenter(EthereumAccountManagerKotlin ethereumAccountManagerKotlin) {

	}

	@Override
	public void viewReady() {

	}

	public void setObservables(Observable<Object> newAccountButton, Observable<Object> deleteAddressButton) {
		newAccountButton.subscribe(clicked -> getView().openCreateAccountScreen());
		deleteAddressButton.subscribe(clicked -> getView().showPasswordConfirmationDialog());
	}

	public void deleteActiveAccount(String password) {
		// Validate password
	}
}
