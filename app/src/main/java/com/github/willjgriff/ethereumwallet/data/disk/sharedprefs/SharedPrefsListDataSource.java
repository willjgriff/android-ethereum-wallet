package com.github.willjgriff.ethereumwallet.data.disk.sharedprefs;

import com.github.willjgriff.ethereumwallet.data.disk.ListDiskDataSource;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Will on 30/12/2016.
 */

public abstract class SharedPrefsListDataSource<TYPE, QUERY> implements ListDiskDataSource<TYPE, QUERY> {

	private SharedPreferencesManager mSharedPreferencesManager;

	public SharedPrefsListDataSource(SharedPreferencesManager sharedPreferencesManager) {
		mSharedPreferencesManager = sharedPreferencesManager;
	}

	@Override
	public Observable<List<TYPE>> getListFromDisk(QUERY query) {
		List<TYPE> dataList = mSharedPreferencesManager.readComplexObjectFromPreferences(getKey(), getTypeToken());
		return Observable.just(dataList);
	}

	@Override
	public void saveListToDisk(List<TYPE> dataList) {
		mSharedPreferencesManager.writeObjectToPreferences(getKey(), dataList);
	}

	protected abstract String getKey();

	protected abstract TypeToken<List<TYPE>> getTypeToken();
}
