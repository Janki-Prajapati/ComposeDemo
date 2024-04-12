package com.jp.test.composedemo.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jp.test.composedemo.databse.Customer
import com.jp.test.composedemo.databse.CustomerRepository
import com.jp.test.composedemo.domain.usecase.ValidateEmailUseCase
import com.jp.test.composedemo.domain.usecase.ValidateFirstNameUseCase
import com.jp.test.composedemo.domain.usecase.ValidateLastNameUseCase
import com.jp.test.composedemo.domain.usecase.ValidatePasswordUseCase
import com.jp.test.composedemo.domain.usecase.ValidatePhoneNumberUseCase
import com.jp.test.composedemo.generic.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val validatePhoneNumberUseCase = ValidatePhoneNumberUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()
    private val validateFirstNameUseCase = ValidateFirstNameUseCase()
    private val validateLastNameUseCase = ValidateLastNameUseCase()

    var formState by mutableStateOf(MainState())

    fun addCustomer(customer: Customer) {
        customerRepository.addCustomer(customer)
    }

     fun findCustomer(eMail: String): LiveData<Int> {
        return customerRepository.findCustomer(eMail)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FirstNameChanged -> {
                formState = formState.copy(fName = event.fName)
                validateFirstName()
            }

            is MainEvent.LastNameChanged -> {
                formState = formState.copy(lName = event.lName)
                validateLastName()
            }

            is MainEvent.PhoneChanged -> {
                formState = formState.copy(phone = event.phone)
                validatePhone()
            }

            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is MainEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            is MainEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }
        }
    }

    fun validateAllFields(): Boolean {
        return validateFirstName() && validateLastName() && validatePhone() && validateEmail() && validatePassword()
    }

    private fun validateFirstName(): Boolean {
        val firstNameRequest = validateFirstNameUseCase.execute(formState.fName)
        formState = formState.copy(fNameError = firstNameRequest.errorMessage)
        return firstNameRequest.successful
    }

    private fun validateLastName(): Boolean {
        val lastNameRequest = validateLastNameUseCase.execute(formState.lName)
        formState = formState.copy(lNameError = lastNameRequest.errorMessage)
        return lastNameRequest.successful
    }

    private fun validatePhone(): Boolean {
        val phoneRequest = validatePhoneNumberUseCase.execute(formState.phone)
        formState = formState.copy(phoneError = phoneRequest.errorMessage)
        return phoneRequest.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }
}

sealed class MainEvent {
    data class FirstNameChanged(val fName: String) : MainEvent()
    data class LastNameChanged(val lName: String) : MainEvent()
    data class PhoneChanged(val phone: String) : MainEvent()
    data class EmailChanged(val email: String) : MainEvent()
    data class PasswordChanged(val password: String) : MainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : MainEvent()
}

data class MainState(
    val fName: String = "",
    val fNameError: UiText? = null,
    val lName: String = "",
    val lNameError: UiText? = null,
    val phone: String = "",
    val phoneError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)