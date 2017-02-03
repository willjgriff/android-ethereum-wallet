package com.github.willjgriff.ethereumwallet.ui.createaccount.mvp;

/**
 * Created by Will on 03/02/2017.
 */

public interface CreateAccountView {

	void hidePasswordError();

	void showPasswordError();

	void openWallet();
}
