package com.github.willjgriff.ethereumwallet.ui.settings.mvp

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface ChangeAddressView {

    fun setAddresses(allAccounts: List<DomainAddress>)

    fun closeScreen()
}