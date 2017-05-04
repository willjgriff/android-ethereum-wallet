package com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class NotEmptyValidator : Validator {

    override fun isValid(text: String): Boolean {
        return text.isNotEmpty()
    }
}