package com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput

import com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators.NotEmptyValidator
import com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators.Validator
import java.util.*

/**
 * Created by williamgriffiths on 04/05/2017.
 *
 * If you want to understand how this works, read:
 * https://medium.com/@JakobUlbrich/flag-attributes-in-android-how-to-use-them-ac4ec8aee7d1#.ivwy7y15b
 */
class ValidatorFactory {

    private val NOT_EMPTY = 1

    fun getValidators(flagSet: Int): List<Validator> {
        val validators = ArrayList<Validator>()

        if (containsFlag(flagSet, NOT_EMPTY)) {
            validators.add(NotEmptyValidator())
        }

        return validators
    }

    private fun containsFlag(flagSet: Int, flag: Int): Boolean {
        return flagSet or flag == flagSet
    }

}