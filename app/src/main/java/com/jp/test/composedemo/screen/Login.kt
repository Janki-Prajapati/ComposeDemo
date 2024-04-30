package com.jp.test.composedemo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jp.test.composedemo.Constants
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.CustomTextFieldApp
import com.jp.test.composedemo.navControllers.Routes
import com.jp.test.composedemo.ui.theme.colorBlue
import com.jp.test.composedemo.ui.theme.colorSilver
import com.jp.test.composedemo.utils.Extensions.makeToast
import com.jp.test.composedemo.utils.PreferencesManager
import com.jp.test.composedemo.viewmodels.CustomerViewModel
import com.jp.test.composedemo.viewmodels.MainEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController) {
    val viewModel = hiltViewModel<CustomerViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current

    val interactionSource =
        remember { MutableInteractionSource() } // or use val interactionSource = MutableInteractionSource()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val isLoggedInClicked = remember {
        mutableStateOf(false)
    }
    val isProgressBarVisible= remember {
        mutableStateOf(false)
    }

    if (isLoggedInClicked.value) {
        if (viewModel.isValidCustomer.id != 0) {
            preferencesManager.saveStringData(
                Constants.PREF_KEY_EMAIL,
                viewModel.isValidCustomer.email
            )
            // Update data and save to SharedPreferences
            preferencesManager.saveBooleanData(
                Constants.PREF_KEY_IS_LOGGED_IN,
                true
            )
            isLoggedInClicked.value = false
            isProgressBarVisible.value = false
            navController.navigate(route = Routes.BottomNav.route)

        } else {
            isProgressBarVisible.value = false
            context.makeToast(context.getString(R.string.strEmailOrPasswordIsIncorrect))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isProgressBarVisible.value) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

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
                        text = stringResource(id = R.string.strLoginToYourAccount),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.height(25.dp))
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
                                        contentDescription = stringResource(id = R.string.strVisible),
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

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.End)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { navController.navigate(route = Routes.ForgotPassword.route) },
                        text = stringResource(R.string.strForgotPassword),
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = colorBlue,
                        textDecoration = TextDecoration.Underline
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3366ff)),
                        content = {
                            Text(
                                text = stringResource(id = R.string.login_screen),
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = {
                            if (viewModel.validateEmailPasswordFields()) {
                                isProgressBarVisible.value = true

                                viewModel.findCustomerWithPassword(
                                    viewModel.formState.email,
                                    viewModel.formState.password
                                )
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(1000)
                                    isLoggedInClicked.value = true
                                }
                            }
                        }
                    )


                    Spacer(modifier = Modifier.height(10.dp))
                    /* Button(
                       onClick = {
                           context.makeToast("Google Sign up Clicked!!")
                       },
                       modifier = Modifier
                           .fillMaxWidth(),
                       shape = RoundedCornerShape(5.dp),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color(0xffcce6ff),
                           contentColor = Color.White
                       )
                   ) {
                       Image(
                           modifier = Modifier
                               .height(20.dp)
                               .width(20.dp),
                           painter = painterResource(id = R.drawable.ic_google),
                           contentDescription = ""
                       )
                       Spacer(modifier = Modifier.width(5.dp))
                       Text(text = "Continue with Google", color = Color(0xff3366ff))
                   }*/

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        Text(
                            text = stringResource(id = R.string.strDontHaveAnAccount),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { navController.navigate(route = Routes.SignUp.route) },
                            text = stringResource(id = R.string.signup_screen),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = colorBlue
                        )
                    }
                }
            }

        }
    }
}

// For displaying preview in the
// Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Login(rememberNavController())
}