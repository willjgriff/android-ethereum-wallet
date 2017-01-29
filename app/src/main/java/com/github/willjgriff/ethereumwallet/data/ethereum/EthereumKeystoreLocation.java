package com.github.willjgriff.ethereumwallet.data.ethereum;

import android.content.Context;

/**
 * Created by Will on 29/01/2017.
 */

public class EthereumKeystoreLocation {

	private Context mContext;

	public EthereumKeystoreLocation(Context context) {
		mContext = context;
	}

	public String getLocation() {
		return mContext.getFilesDir() + "/keystore";
	}
}
