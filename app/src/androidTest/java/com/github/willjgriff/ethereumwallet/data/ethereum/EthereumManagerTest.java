package com.github.willjgriff.ethereumwallet.data.ethereum;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.willjgriff.ethereumwallet.BuildConfig;

import org.ethereum.geth.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Will on 29/01/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EthereumManagerTest {

	private EthereumManager mSubject;

	private final String ACCOUNT_PASSWORD = "password";
//	@Mock
	private EthereumKeystoreLocation mMockEthereumKeystoreLocation;

	@Before
	public void setupEthereumManagerTest() {
//		MockitoAnnotations.initMocks(this);
		mSubject = new EthereumManager(mMockEthereumKeystoreLocation);

		Context appContext = InstrumentationRegistry.getTargetContext();
		mMockEthereumKeystoreLocation = new EthereumKeystoreLocation(appContext);
//		Mockito.when(mMockEthereumKeystoreLocation.getLocation()).thenReturn("/mocklocation");
	}

	@Test
	public void getAccountWithNoAccount_returnsNull() {
		Account account = mSubject.getAccount();

		Assert.assertNull(account);
	}

	@Test
	public void getAccountAfterCreatingAccount_returnsCreatedAccount() {
		mSubject.createAccount(ACCOUNT_PASSWORD);

		Account account = mSubject.getAccount();

		Assert.assertNotNull(account);
	}

}