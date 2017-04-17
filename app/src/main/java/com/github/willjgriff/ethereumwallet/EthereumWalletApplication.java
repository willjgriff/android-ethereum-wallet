package com.github.willjgriff.ethereumwallet;

import android.app.Application;
import android.content.Context;

import com.github.wiljgriff.ethereumwallet.di.AppInjector;
import com.github.wiljgriff.ethereumwallet.ethereum.EthereumManager;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class EthereumWalletApplication extends Application {

	// TODO: Remove for production, just for testing purposes.
	private static EthereumWalletApplication mEthereumWalletApplication;
	public static Context getApp() {
		return mEthereumWalletApplication;
	}

	@Inject
	EthereumManager mEthereumManager;

	@Override
	public void onCreate() {
		super.onCreate();
		mEthereumWalletApplication = this;
		setupLeakCanary();
		setupTimber();
		setupRealm();
		setupDagger();
	}

	private void setupLeakCanary() {
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);
		// Normal app init code...
	}

	private void setupTimber() {
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	private void setupRealm() {
		Realm.init(this);

		// TODO: Delete for prod, test migrations first.
		RealmConfiguration realmConfig = new RealmConfiguration.Builder()
			.deleteRealmIfMigrationNeeded()
			.build();

		Realm.setDefaultConfiguration(realmConfig);
	}

	private void setupDagger() {
		AppInjector.INSTANCE.init(this);
		AppInjector.INSTANCE.getAppComponent().inject(this);
		mEthereumManager.startEthereum();
	}
}
