# android-ethereum-wallet

***For the time being I have stopped working on this app in favour of learning more about Ethereum and specifically Solidity. See https://github.com/willjgriff/solidity-playground***

This app will be a front end for an Ethereum light client. To begin with I will use the Go Ethereum implementation since it is easy to import. However, this may drain the mobile's resources, it at least seems to use a lot of storage in preliminary builds once fully sycn'd (400MB+). I believe the current version of the light client doesn't remove unused data (mainly block headers I think) but in the future it should do, reducing this size. I may also experiment with connecting to an externally hosted node. 

It will allow for basic account/address creation and transfer to/from it. The account may be extractable for use in other wallets. The behaviour and capability of the app will be largely dependant on the services provided by the light client.

This app uses a structure similar to my Skeleton app. It is written in Kotlin and uses MVP, Dagger2, RxJava2 and only uses one Activity with Conductor for View management.

I've created Geth adapters that wrap around the Geth light client classes to decouple them from the rest of the app. I also cannot Unit Test anything that uses the Geth light client classes without instrumentation (a link error is returned when run as standard JUnit tests). Decoupling the Geth classes from the rest of the code allows me to Unit Test the Ethereum manager classes without accessing the actual Geth classes. I'm not testing much at the moment generally. More will come later.

Current progress (the UI is still very basic, it will be made better if completed):<br>
[x] Creation, deletion and switching of account addresses from the Settings screen.<br>
[ ] Importing and exporting account addresses.<br>
[x] Node status screen which displays syncing updates.<br>
[x] Receive screen balance updates.<br>
[x] Send screen submit transaction.<br>
[ ] Creation of QR code for receiving ether.<br>
[ ] Creation of send transaction from QR code.<br>
[ ] Transactions list, outgoing and incoming. (I have made an implementation for this but it is buggy. It is also inefficient to get and store the transaction list manually. This will be switched to use a third party, probably etherscan.io)
