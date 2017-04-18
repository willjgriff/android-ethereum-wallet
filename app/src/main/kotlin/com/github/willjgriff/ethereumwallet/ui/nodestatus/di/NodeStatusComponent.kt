package com.github.willjgriff.ethereumwallet.ui.nodestatus.di

import com.github.willjgriff.ethereumwallet.ui.nodestatus.NodeStatusController
import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import dagger.Component

/**
 * Created by Will on 21/03/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface NodeStatusComponent {

    fun inject(nodeStatusController: NodeStatusController)
}