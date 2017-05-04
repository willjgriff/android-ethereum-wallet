package com.github.willjgriff.ethereumwallet.ui.settings

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpAlertDialog
import com.github.willjgriff.ethereumwallet.ui.settings.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.DeleteAddressPresenter
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.DeleteAddressView
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
        val deleteButton = RxView.clicks(getButton(DialogInterface.BUTTON_POSITIVE)).share()
        val cancelButton = RxView.clicks(getButton(DialogInterface.BUTTON_NEGATIVE)).share()
        mValidatedTextInputLayout.setCheckValidationTrigger(deleteButton)
        val isTextValid = mValidatedTextInputLayout.textValidObservable
        val textChanged = mValidatedTextInputLayout.textChangedObservable

        presenter.setObservables(deleteButton, cancelButton, isTextValid, textChanged)
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