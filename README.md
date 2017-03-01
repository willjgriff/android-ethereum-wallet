# android-ethereum-wallet

This is a work in progress.

This app will be a front end for the Geth light client. It will allow for basic account/address creation and transfer to/from it. The account may be extractable for use in other wallets. The behaviour and capability of the app will be largely dependant on the services the Geth light client provides.

This app uses a structure similar to my Skeleton app. It uses MVP with Dagger, RxJava2 and RxViewBindings. I am experimenting with Constraint Layouts and Conductor as a replacement for Fragments. I'm currently using Kotlin for testing and for Ethereum related classes. I expect to use Kotlin more as I understand it.

I've created a set of delegate classes that wrap around the Geth light client classes as I cannot Unit Test anything that uses the Geth light client classes without instrumentation (a link error is returned when run as standard JUnit tests). The decoupling the delegate classes provide allows me to Unit Test the Ethereum related classes without accessing the actual Geth classes.

The app currently only allows creation, deletion and switching of account addresses fairly crudely from the Settings screen and some basic wireframes for the other screens.
