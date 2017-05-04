package com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput

import com.github.willjgriff.ethereumwallet.ui.widget.validatedtextinput.validators.Validator
import io.reactivex.Observable

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class RxTextInputValidation(private val validTextInputListener: RxTextInputValidation.ValidTextInputListener,
                            private val validators: List<Validator>,
                            textChangedObservable: Observable<CharSequence>) {

    lateinit var textValidObservable: Observable<Boolean>
        private set

    val textChangedObservable: Observable<String> = textChangedObservable
            .map { it.toString() }
            .distinctUntilChanged()

    init {
        // TODO: Debug these Observables and check they behave as expected (they may be firing to often)
        setupTextValidObservable()
    }

    fun setupTextValidObservable() {
        textValidObservable = textChangedObservable
                .flatMap { isTextValid(it) }
                .map { validText -> validText }
                .distinctUntilChanged()

        // Hide the error when valid text is emitted
        textValidObservable
                .filter { isValid -> isValid }
                .subscribe { _ -> validTextInputListener.hideError() }

        // Skip until the field has a valid result
        val hotValidText = textValidObservable
                .publish()
                .autoConnect()
        val skipUntilValid = hotValidText
                .skipUntil(hotValidText.filter { isValid -> isValid })

        // Show the error when invalid text is emitted
        skipUntilValid
                .filter { isValid -> !isValid }
                .subscribe { _ -> validTextInputListener.showError() }
    }

    private fun isTextValid(inputText: String): Observable<Boolean> {
        return Observable.fromIterable(validators)
                .all { validator -> validator.isValid(inputText) }
                .toObservable()
    }

    fun setValidateTrigger(validateTrigger: Observable<Any>) {
        validateTrigger
                .flatMap { _ -> textValidObservable }
                .filter { isValid -> !isValid }
                .subscribe { _ -> validTextInputListener.showError() }
    }

    interface ValidTextInputListener {
        fun showError()
        fun hideError()
    }
}