package com.github.wiljgriff.ethereumwallet.ui.nodestatus.di

import com.github.wiljgriff.ethereumwallet.di.AppComponent
import com.github.wiljgriff.ethereumwallet.di.ControllerScope
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.NodeStatusController
import dagger.Component

/**
 * Created by Will on 21/03/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface NodeStatusComponent {

    fun inject(nodeStatusController: NodeStatusController)
}