package com.github.willjgriff.ethereumwallet.data;

import io.reactivex.Observable;

/**
 * Created by Will on 31/12/2016.
 */

public interface RefreshableRepository {

	void setRefreshTrigger(Observable<Void> refreshTrigger);
}
