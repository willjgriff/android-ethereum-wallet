package com.github.willjgriff.ethereumwallet.data.disk;

import io.reactivex.Observable;

/**
 * Created by Will on 29/01/2017.
 */

public interface DiskDataSource<TYPE, QUERY> {

	Observable<TYPE> getFromDisk(QUERY query);

	void saveToDisk(TYPE data);
}
