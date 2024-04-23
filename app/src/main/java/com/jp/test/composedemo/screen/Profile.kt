package com.jp.test.composedemo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.test.composedemo.Constants
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.CustomTextFieldApp
import com.jp.test.composedemo.databse.Customer
import com.jp.test.composedemo.ui.theme.colorSilver
import com.jp.test.composedemo.utils.PreferencesManager
import com.jp.test.composedemo.viewmodels.CustomerViewModel
import com.jp.test.composedemo.viewmodels.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
    val context = LocalContext.current
    val viewModel = hiltViewModel<CustomerViewModel>()
    val preferencesManager = remember { PreferencesManager(context) }

    viewModel.getCustomerData(preferencesManager.getStringData(Constants.PREF_KEY_EMAIL, ""))
    val data = viewModel.customerData.observeAsState().value

    if (data != null) {

        viewModel.onEvent(MainEvent.FirstNameChanged(data.firstName))
        viewModel.onEvent(MainEvent.LastNameChanged(data.lastName))
        viewModel.onEvent(MainEvent.PhoneChanged(data.phone))
        viewModel.onEvent(MainEvent.EmailChanged(data.email))
        viewModel.onEvent(MainEvent.PasswordChanged(data.password))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strfName),
                text = viewModel.formState.fName,
                onValueChange = {
                    viewModel.onEvent(MainEvent.FirstNameChanged(it))
                },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = viewModel.formState.fNameError != null,
                errorMessage = viewModel.formState.fNameError,
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strlName),
                text = viewModel.formState.lName,
                onValueChange = {
                    viewModel.onEvent(MainEvent.LastNameChanged(it))
                },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = viewModel.formState.lNameError != null,
                errorMessage = viewModel.formState.lNameError,
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strPhone),
                text = viewModel.formState.phone,

                onValueChange = {
                    viewModel.onEvent(MainEvent.PhoneChanged(it))
                },
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = viewModel.formState.phoneError != null,
                errorMessage = viewModel.formState.phoneError,
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strEmail),
                text = viewModel.formState.email,
                onValueChange = {
                    viewModel.onEvent(MainEvent.EmailChanged(it))
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = viewModel.formState.emailError != null,
                errorMessage = viewModel.formState.emailError,
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextFieldApp(
                placeholder = stringResource(id = R.string.strPassword),
                text = viewModel.formState.password,
                onValueChange = {
                    viewModel.onEvent(MainEvent.PasswordChanged(it))
                },
                keyboardType = KeyboardType.Password,
                ImeAction.Done,
                trailingIcon = {
                    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                        IconButton(
                            onClick =
                            {
                                viewModel.onEvent(MainEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.formState.isVisiblePassword)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                                contentDescription = "Visible",
                                tint = colorSilver,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                isVisible = viewModel.formState.isVisiblePassword,
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = viewModel.formState.passwordError != null,
                errorMessage = viewModel.formState.passwordError
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (viewModel.validateAllFields()) {

                        var customerId = 0
                        data?.let { customerId = it.id }
                        updateDataToDb(
                            viewModel,
                            Customer(
                                customerId,
                                viewModel.formState.fName,
                                viewModel.formState.lName,
                                viewModel.formState.email,
                                viewModel.formState.phone,
                                viewModel.formState.password
                            )
                        )

                    }

                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3366ff)),
                content = {
                    Text(text = "Update", textAlign = TextAlign.Center)
                }
            )
        }

    }
}

fun updateDataToDb(viewModel: CustomerViewModel, customer: Customer) {
    viewModel.updateCustomer(customer)
}

@Preview
@Composable
private fun ProfilePreview() {
    Profile()
}