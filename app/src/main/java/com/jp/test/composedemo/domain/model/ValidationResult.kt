package com.jp.test.composedemo.domain.model

import com.jp.test.composedemo.generic.UiText

data class ValidationResult(    val successful: Boolean,
                                val errorMessage: UiText? = null)
