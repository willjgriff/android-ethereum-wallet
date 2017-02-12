package com.github.willjgriff.ethereumwallet.di.modules;

import android.content.Context;

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
	Context providesApplications() {
		return mContext;
	}
}
