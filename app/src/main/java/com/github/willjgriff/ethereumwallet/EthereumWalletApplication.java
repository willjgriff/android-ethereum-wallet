package com.github.willjgriff.ethereumwallet;

import android.app.Application;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class EthereumWalletApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
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
		ApplicationInjector.INSTANCE.initAppComponent(this);
	}
}
