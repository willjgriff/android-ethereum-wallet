package com.github.willjgriff.ethereumwallet

import android.app.Application

import android.content.Context
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class EthereumWalletApplication : Application() {

    // TODO: Remove for production, just for testing purposes.
    companion object {
        private lateinit var appObject: EthereumWalletApplication
        val app: Context
            get() = appObject
    }

    override fun onCreate() {
        super.onCreate()
        appObject = this
        setupLeakCanary()
        setupTimber()
        setupRealm()
        setupDagger()
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupRealm() {
        Realm.init(this)

        // TODO: Delete for prod, test migrations first.
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun setupDagger() {
        AppInjector.init(this)
        // This currently starts the Ethereum node, consider adding a call for this
        AppInjector.appComponent.inject(this)
    }
}