package com.github.willjgriff.ethereumwallet.utils.resettablelazy

/**
 * Created by williamgriffiths on 28/04/2017.
 */
fun <PROPTYPE> resettableLazy(manager: ResettableLazyManager, init: ()->PROPTYPE): ResettableLazy<PROPTYPE> {
    return ResettableLazy(manager, init)
}