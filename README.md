# android-ethereum-wallet

This is a work in progress.

This app will be a front end for the Geth light client. It will allow for basic account/address creation and transfer to/from it. The account may be extractable for use in other wallets. The behaviour and capability of the app will be largely dependant on the services the Geth light client provides.

This app uses a structure similar to my Skeleton app. It uses MVP with Dagger, RxJava2 and ViewBindings. I've started using Constraint Layouts and ButterKnife too. I am experimenting with Conductor as a replacement for Fragments. I'm using Kotlin for testing and in a subset of the main code. I expect to use Kotlin more as I understand it.

I'm currently figuring out a way to Unit Test any wrapper I make around the Ethereum library. I currently have to do instrumentation tests  as accessing the Ethereum library (even just for mocking) returns a link error if not run within an emulator. 
