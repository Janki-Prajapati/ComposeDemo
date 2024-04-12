package com.jp.test.composedemo.domain.usecase

import com.jp.test.composedemo.generic.usecase.BaseUseCase
import com.jp.test.composedemo.R
import com.jp.test.composedemo.generic.UiText
import com.jp.test.composedemo.domain.model.ValidationResult
import com.jp.test.composedemo.isPasswordValid

class ValidatePasswordUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToConsistOfAtLeastEightCharacters),
            )
        }

        if (!isPasswordValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToContainAtLeastOneLetterAndDigit),
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}