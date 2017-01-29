package com.github.willjgriff.ethereumwallet.data.disk;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Will on 29/01/2017.
 */

public interface ListDiskDataSource<TYPE, QUERY> {

	Observable<List<TYPE>> getListFromDisk(QUERY query);

	void saveListToDisk(List<TYPE> dataList);
}
