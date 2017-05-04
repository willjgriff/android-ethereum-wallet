package com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.text.InputType
import android.util.AttributeSet
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators.Validator
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import java.util.*

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class ValidatedTextInputLayout : TextInputLayout, RxTextInputValidation.ValidTextInputListener {

    private var validators: List<Validator> = ArrayList()
    private var errorMessage: CharSequence = ""
    private lateinit var rxTextInputValidation: RxTextInputValidation

    val textChangedObservable: Observable<String>
        get() = rxTextInputValidation.textChangedObservable

    val textValidObservable: Observable<Boolean>
        get() = rxTextInputValidation.textValidObservable

    val text: String
        get() = editText!!.text.toString()

    init {
        context.inflate(R.layout.view_validated_text_input_layout, this, true)
    }

    constructor(context: Context) : super(context) {
        setupObservables()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupAttributes(attrs)
        setupObservables()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupAttributes(attrs)
        setupObservables()
    }

    private fun setupObservables() {
        val textChanged = RxTextView.textChanges(editText!!)
        rxTextInputValidation = RxTextInputValidation(this, validators, textChanged)
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValidatedTextInputLayout)
        try {
            val inputType = typedArray.getInteger(R.styleable.ValidatedTextInputLayout_android_inputType, InputType.TYPE_CLASS_TEXT)
            editText!!.inputType = inputType

            val validatorsFlag = typedArray.getInteger(R.styleable.ValidatedTextInputLayout_validator, 0)
            validators = ValidatorFactory().getValidators(validatorsFlag)

            errorMessage = typedArray.getText(R.styleable.ValidatedTextInputLayout_error_text)

        } finally {
            typedArray.recycle()
        }
    }

    fun setCheckValidationTrigger(validateTrigger: Observable<Any>) {
        rxTextInputValidation.setValidateTrigger(validateTrigger)
    }

    override fun showError() {
        error = errorMessage
    }

    override fun hideError() {
        isErrorEnabled = false
    }
}