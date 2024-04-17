package com.jp.test.composedemo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.CustomTextFieldApp
import com.jp.test.composedemo.databse.Customer
import com.jp.test.composedemo.ui.theme.colorSilver
import com.jp.test.composedemo.utils.Extensions.makeToast
import com.jp.test.composedemo.viewmodels.CustomerViewModel
import com.jp.test.composedemo.viewmodels.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(onClick: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel = hiltViewModel<CustomerViewModel>()
    val openDialog = remember {
        mutableStateOf(false)
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Alert",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = context.getString(R.string.strThisEmailIsAlreadyRegisteredPleaseTryWithAnotherOne),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        modifier = Modifier
                            .wrapContentWidth(align = Alignment.End)
                            .align(Alignment.End)
                            .clickable {
                                openDialog.value = false
                            },
                        text = context.getString(R.string.strOk),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create an account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(25.dp))

                CustomTextFieldApp(
                    placeholder = stringResource(id = R.string.strfName),
                    text = viewModel.formState.fName,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.FirstNameChanged(it))
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    isError = viewModel.formState.passwordError != null,
                    errorMessage = viewModel.formState.passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (viewModel.validateAllFields()) {

                            viewModel.findCustomer(viewModel.formState.email)
                                .observe(lifecycleOwner) {
                                    if (it > 0) {
                                        openDialog.value = true
                                        context.makeToast(context.getString(R.string.strThisEmailIsAlreadyRegisteredPleaseTryWithAnotherOne))
                                    } else {
                                        saveDataToDb(
                                            viewModel,
                                            Customer(
                                                firstName = viewModel.formState.fName,
                                                lastName = viewModel.formState.lName,
                                                email = viewModel.formState.email,
                                                phone = viewModel.formState.phone,
                                                password = viewModel.formState.password
                                            )
                                        )
                                        onClick("signup")
                                    }

                                }

                        }

                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3366ff)),
                    content = {
                        Text(text = "Create an account", textAlign = TextAlign.Center)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = "Already have an account?",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        modifier = Modifier.clickable {
                            onClick("login")
                        },
                        text = "Login",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = Color(0xff66b3ff)
                    )
                }
            }
        }
    }
}


fun saveDataToDb(viewModel: CustomerViewModel, customer: Customer) {
    viewModel.addCustomer(customer)
}


// For displaying preview in the
// Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp(onClick = {})
}