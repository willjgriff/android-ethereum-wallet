package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

import com.github.willjgriff.ethereumwallet.data.disk.sharedprefs.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 29/01/2017.
 */

@Module
public class AppModule {

	private Context mContext;

	public AppModule(Context context) {
		mContext = context;
	}

	@Provides
	@Singleton
	Context providesContext() {
		return mContext;
	}

	@Provides
	@Singleton
	SharedPreferences providesSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(mContext);
	}
}
