package com.github.willjgriff.ethereumwallet.data.ethereum;

import com.github.willjgriff.ethereumwallet.BuildConfig;
import com.github.willjgriff.ethereumwallet.EthereumWalletApplication;

import org.ethereum.geth.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by Will on 29/01/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23, packageName = "com.github.willjgriff.ethereumwallet", application = EthereumWalletApplication.class)
public class EthereumManagerTest {

	private EthereumManager mSubject;

	private final String ACCOUNT_PASSWORD = "password";
	@Mock
	private EthereumKeystoreLocation mMockEthereumKeystoreLocation;

	@Before
	public void setupEthereumAccountTest() {
		MockitoAnnotations.initMocks(this);
		mSubject = new EthereumManager(mMockEthereumKeystoreLocation);
		mMockEthereumKeystoreLocation = new EthereumKeystoreLocation(RuntimeEnvironment.application);
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