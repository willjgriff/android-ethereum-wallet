package com.github.willjgriff.ethereumwallet.ui.screens.settings

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpAlertDialog
import com.github.willjgriff.ethereumwallet.ui.screens.settings.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp.DeleteAddressPresenter
import com.github.willjgriff.ethereumwallet.ui.screens.settings.mvp.DeleteAddressView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.ValidatedTextInputLayout
import com.jakewharton.rxbinding2.view.RxView
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class DeleteAddressAlertDialog(context: Context, private val mDialogListener: SettingsDeleteAlertDialogListener)
    : BaseMvpAlertDialog<DeleteAddressView, DeleteAddressPresenter>(context), DeleteAddressView {

    override val mvpView: DeleteAddressView
        get() = this
    @Inject override lateinit var presenter: DeleteAddressPresenter

    private var mValidatedTextInputLayout: ValidatedTextInputLayout =
            context.inflate(R.layout.view_controller_settings_delete_validated_input) as ValidatedTextInputLayout

    init {
        injectPresenter()
        setupAppearance()
    }

    private fun setupAppearance() {
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.controller_settings_delete_address)) { _, _ -> }
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.controller_settings_delete_address_cancel)) { _, _ -> }
        setTitle(R.string.controller_settings_delete_address_dialog_title)
        setMessage(context.getString(R.string.controller_settings_delete_address_dialog_message))
        setView(mValidatedTextInputLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservablesOnPresenter()
    }

    private fun setupObservablesOnPresenter() {
        val deleteClick = RxView.clicks(getButton(DialogInterface.BUTTON_POSITIVE)).share()
        val cancelClick = RxView.clicks(getButton(DialogInterface.BUTTON_NEGATIVE)).share()
        mValidatedTextInputLayout.setCheckValidationTrigger(deleteClick)
        val passwordValid = mValidatedTextInputLayout.textValidObservable
        val passwordChanged = mValidatedTextInputLayout.textChangedObservable

        presenter.apply {
            this.deleteClick = deleteClick
            this.cancelClick = cancelClick
            this.passwordValid = passwordValid
            this.passwordChanged = passwordChanged
        }
    }

    override fun closeDialog() {
        dismiss()
    }

    override fun incorrectPasswordEntered() {
        mDialogListener.showIncorrectPasswordDialog()
    }

    override fun addressDeleted() {
        mDialogListener.addressSuccessfullyDeleted()
    }

    interface SettingsDeleteAlertDialogListener {
        fun showIncorrectPasswordDialog()
        fun addressSuccessfullyDeleted()
    }
}