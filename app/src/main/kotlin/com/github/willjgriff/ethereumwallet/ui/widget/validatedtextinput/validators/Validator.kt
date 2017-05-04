package com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators

/**
 * Created by williamgriffiths on 04/05/2017.
 */
interface Validator {

    fun isValid(text: String): Boolean
}