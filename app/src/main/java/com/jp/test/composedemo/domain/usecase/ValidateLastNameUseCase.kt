package com.jp.test.composedemo.domain.usecase

import com.jp.test.composedemo.R
import com.jp.test.composedemo.domain.model.ValidationResult
import com.jp.test.composedemo.generic.UiText
import com.jp.test.composedemo.generic.usecase.BaseUseCase

class ValidateLastNameUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheLastNameCanNotBeBlank)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}