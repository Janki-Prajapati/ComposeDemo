package com.jp.test.composedemo.domain.usecase

import com.jp.test.composedemo.generic.usecase.BaseUseCase
import com.jp.test.composedemo.R
import com.jp.test.composedemo.generic.UiText
import com.jp.test.composedemo.domain.model.ValidationResult
import com.jp.test.composedemo.isNumber
import com.jp.test.composedemo.isNumberLength

class ValidatePhoneNumberUseCase : BaseUseCase<String, ValidationResult> {

    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneCanNotBeBlank),
            )
        }

        if (!isNumberLength(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldContain10Digit),
            )
        }

        if (!isNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldBeContentJustDigit),
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}