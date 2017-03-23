package com.github.wiljgriff.ethereumwallet.ui.nodestatus.di

import com.github.wiljgriff.ethereumwallet.ui.nodestatus.NodeStatusController
import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.FunctionScope
import dagger.Component

/**
 * Created by Will on 21/03/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@FunctionScope
interface NodeStatusComponent {

    fun inject(nodeStatusController: NodeStatusController)
}