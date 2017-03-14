package com.github.willjgriff.ethereumwallet.ui.send.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.EthereumWalletApplication;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import org.ethereum.geth.Account;
import org.ethereum.geth.AccountManager;
import org.ethereum.geth.Address;
import org.ethereum.geth.Bloom;
import org.ethereum.geth.Context;
import org.ethereum.geth.EthereumClient;
import org.ethereum.geth.Geth;
import org.ethereum.geth.Hash;
import org.ethereum.geth.Node;
import org.ethereum.geth.NodeConfig;
import org.ethereum.geth.NodeInfo;
import org.ethereum.geth.Transaction;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 04/02/2017.
 */

@FunctionScope
public class SendPresenter extends BaseMvpPresenter<SendView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;
	private Observable<Object> mSendEther;

	@Inject
	public SendPresenter(EthereumAccountManagerKotlin ethereumAccountManager) {
		mEthereumAccountManager = ethereumAccountManager;
	}

	@Override
	public void viewReady() {

		AccountManager accountManager = new AccountManager(EthereumWalletApplication.getApp().getFilesDir() + "/keystore/path", Geth.LightScryptN, Geth.LightScryptP);
		try {
			Account account = accountManager.newAccount("Password");

//			Hash transactionHash = new Hash("0xarif3948j3");

			byte[] transactionSignature = accountManager.signWithPassphrase(account.getAddress(), "Password", transactionHash.getBytes());
			Transaction transaction = new Transaction().withSignature(transactionSignature);


			Context context = new Context();
			Node node = new Node(EthereumWalletApplication.getApp().getFilesDir() + "/some/path", new NodeConfig());
			NodeInfo nodeInfo = node.getNodeInfo();
			nodeInfo.getID();

//			EthereumClient ethereumClient = new EthereumClient();
//			ethereumClient.sendTransaction(, transaction);
//
//			ethereumClient.getBalanceAt()

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setObservables(Observable<CharSequence> recipientAddressObservable, Observable<CharSequence> sendAmountObservable, Observable<CharSequence> accountPasswordObservable, Observable<Object> sendObservable) {
		mSendEther = sendObservable;
		mSendEther.subscribe(o -> getView().toString());
	}

	@Override
	public void releaseViewReferences() {
		mSendEther = null;
	}
}
