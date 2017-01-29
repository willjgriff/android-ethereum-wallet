package com.github.willjgriff.ethereumwallet.ui;

import android.view.MenuItem;

import com.bluelinelabs.conductor.Controller;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.receive.ReceiveController;
import com.github.willjgriff.ethereumwallet.ui.send.SendController;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

/**
 * Created by Will on 29/01/2017.
 */

public class NavigationControllerFactory {

	public Controller getController(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.navigation_transactions:
				return new TransactionsController();
			case R.id.navigation_send:
				return new SendController();
			case R.id.navigation_receive:
				return new ReceiveController();
		}
		return null;
	}
}
