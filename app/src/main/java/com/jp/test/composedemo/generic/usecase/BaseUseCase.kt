package com.jp.test.composedemo.generic.usecase

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}