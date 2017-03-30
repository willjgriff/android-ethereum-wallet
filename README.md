# android-ethereum-wallet

This is a work in progress.

This app will be a front end for an Ethereum light client. To begin with I will use the Go Ethereum implementation since it is easy to import. However, I am aware this may significantly drain the mobile's resources. I may also experiment with connecting to an externally hosted node. 

It will allow for basic account/address creation and transfer to/from it. The account may be extractable for use in other wallets. The behaviour and capability of the app will be largely dependant on the services provided by the light client.

This app uses a structure similar to my Skeleton app. It uses MVP, Dagger, RxJava2 and Conductor. I started by using Kotlin for testing and for Ethereum related classes but will slowly convert the whole project to it removing Java wherever possible.

I've created a set of delegate classes that wrap around the Geth light client classes as I cannot Unit Test anything that uses the Geth light client classes without instrumentation (a link error is returned when run as standard JUnit tests). The decoupling the delegate classes provide allows me to Unit Test the Ethereum related classes without accessing the actual Geth classes.

The app currently only allows creation, deletion and switching of account addresses fairly crudely from the Settings screen and some basic wireframes for the other screens.
