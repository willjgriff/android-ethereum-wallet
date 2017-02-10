package com.github.willjgriff.ethereumwallet.data.ethereum;

import org.ethereum.geth.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by Will on 29/01/2017.
 *
 * TODO: Probably delete this and stick to the Kotlin Instrumentation tests.
 */
public class EthereumAccountManagerAndroidTest {

	private final String MOCK_PASSWORD = "password";
	private final String MOCK_FILE_PATH = "/mockLocation";

	private EthereumAccountManager mSubject;

	@Mock
	private EthereumKeystoreLocation mMockEthereumKeystoreLocation;

	@Before
	public void setupEthereumManagerTest() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(mMockEthereumKeystoreLocation.getLocation()).thenReturn(MOCK_FILE_PATH);

		mSubject = new EthereumAccountManager(mMockEthereumKeystoreLocation);
	}

	@Test
	public void getAccountWithNoAccount_returnsNull() {
		Account account = mSubject.getAccount();

		Assert.assertNull(account);
	}

	@Test
	public void getAccountAfterCreatingAccount_returnsCreatedAccount() {
		mSubject.createAccount(MOCK_PASSWORD);

		Account account = mSubject.getAccount();

		Assert.assertNotNull(account);
	}

}