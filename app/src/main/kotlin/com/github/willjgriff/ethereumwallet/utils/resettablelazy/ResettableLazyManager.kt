package com.github.willjgriff.ethereumwallet.utils.resettablelazy

import java.util.*

/**
 * Created by williamgriffiths on 28/04/2017.
 */
class ResettableLazyManager {
    // we synchronize to make sure the timing of a reset() call and new inits do not collide
    val managedDelegates = LinkedList<Resettable>()

    fun register(managed: Resettable) {
        synchronized (managedDelegates) {
            managedDelegates.add(managed)
        }
    }

    fun reset() {
        synchronized (managedDelegates) {
            managedDelegates.forEach { it.reset() }
            managedDelegates.clear()
        }
    }

    interface Resettable {
        fun reset()
    }
}