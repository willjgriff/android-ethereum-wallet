package com.github.willjgriff.ethereumwallet.ethereum.node

import com.github.willjgriff.ethereumwallet.ethereum.node.model.DomainBlockHeader
import io.reactivex.Observable
import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Header
import org.ethereum.geth.NewHeadHandler

/**
 * Created by williamgriffiths on 26/04/2017.
 */
class NewBlockHeaderAdapter(private val ethereumClient: EthereumClient,
                            private val context: Context) {

    // TODO: Find out what this number means. It may be MB of cache for lightchaindata.
    private val SOME_RANDOM_SAMPLING_SIZE = 16L
    val newBlockHeaderObservable: Observable<DomainBlockHeader> by lazy { getNewBlockHeader() }

    private fun getNewBlockHeader(): Observable<DomainBlockHeader> {
        return Observable
                .create<DomainBlockHeader> {
                    ethereumClient.subscribeNewHead(context, object : NewHeadHandler {
                        override fun onNewHead(header: Header) {
                            it.onNext(DomainBlockHeader.fromHeader(header))
                        }

                        override fun onError(error: String) {
                            it.onError(Throwable(error))
                        }

                    }, SOME_RANDOM_SAMPLING_SIZE)
                }
                .share()
    }
}