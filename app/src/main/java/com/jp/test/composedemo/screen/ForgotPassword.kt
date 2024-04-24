package com.jp.test.composedemo.screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.jp.test.composedemo.NotificationService
import com.jp.test.composedemo.R
import com.jp.test.composedemo.components.CustomTextFieldApp
import com.jp.test.composedemo.generatePIN
import com.jp.test.composedemo.ui.theme.colorSilver
import com.jp.test.composedemo.viewmodels.CustomerViewModel
import com.jp.test.composedemo.viewmodels.MainEvent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ForgotPassword(navHostController: NavHostController) {
    val viewModel = hiltViewModel<CustomerViewModel>()

    val isCodeTextFieldVisible = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val showRationalDialog = remember { mutableStateOf(false) }
    val isPasswordChange = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Remember show notification Permission State
    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val notificationService = NotificationService(context = LocalContext.current)

    LaunchedEffect(key1 = Unit) {
        if (!notificationPermission.status.isGranted) {
            if (notificationPermission.status.shouldShowRationale) {
                // Show a rationale if needed (optional)
                showRationalDialog.value = true
            } else {
                // Request the permission
                notificationPermission.launchPermissionRequest()
            }
        }
    }

    if (showRationalDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showRationalDialog.value = false
            },
            title = {
                Text(
                    text = "Permission",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Text(
                    "The notification is important for this app. Please grant the permission.",
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRationalDialog.value = false
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(context, intent, null)

                    }) {
                    Text("OK", style = TextStyle(color = Color.Black))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRationalDialog.value = false
                    }) {
                    Text("Cancel", style = TextStyle(color = Color.Black))
                }
            },
        )
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
                if (isPasswordChange.value) {
                    navHostController.navigateUp()
                }
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
                        text = dialogMessage.value,
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
                                if (isPasswordChange.value) {
                                    navHostController.navigateUp()
                                }
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
            .background(Color(0xfff6f6f6))
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
                    text = "Reset your password",
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
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = viewModel.formState.emailError != null,
                    errorMessage = viewModel.formState.emailError,
                )
                Spacer(modifier = Modifier.height(15.dp))
                CustomTextFieldApp(
                    placeholder = stringResource(id = R.string.strNewPassword),
                    text = viewModel.formState.password,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.PasswordChanged(it))
                    },
                    keyboardType = KeyboardType.Password,
                    ImeAction.Done,
                    trailingIcon = {
                        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                            IconButton(onClick = {
                                viewModel.onEvent(MainEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
                            }) {
                                Icon(
                                    imageVector = if (viewModel.formState.isVisiblePassword) Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff,
                                    contentDescription = "Visible",
                                    tint = colorSilver,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    isVisible = viewModel.formState.isVisiblePassword,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = viewModel.formState.passwordError != null,
                    errorMessage = viewModel.formState.passwordError
                )

                Spacer(modifier = Modifier.height(15.dp))

                if (isCodeTextFieldVisible.value) {
                    CustomTextFieldApp(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = stringResource(id = R.string.strCode),
                        text = viewModel.formState.code,
                        onValueChange = {
                            viewModel.onEvent(MainEvent.CodeChanged(it))
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                        singleLine = true,
                        isError = viewModel.formState.codeError != null,
                        errorMessage = viewModel.formState.codeError
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (!isCodeTextFieldVisible.value) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3366ff)),
                        content = {
                            Text(text = "Send Code", textAlign = TextAlign.Center)
                        },
                        onClick = {

                            if (!notificationPermission.status.isGranted) {
                                if (notificationPermission.status.shouldShowRationale) {
                                    // Show a rationale if needed (optional)
                                    showRationalDialog.value = true
                                } else {
                                    // Request the permission
                                    notificationPermission.launchPermissionRequest()

                                }
                            } else {
                                if (viewModel.validateEmailPasswordFields()) {
                                    viewModel.findCustomer(viewModel.formState.email)
                                        .observe(lifecycleOwner) {
                                            if (it > 0) {
                                                isCodeTextFieldVisible.value = true
                                                notificationService.showBasicNotification(
                                                    generatePIN()
                                                )
                                            } else {
                                                dialogMessage.value =
                                                    context.getString(R.string.strThisEmailIsNotRegisteredPleaseTryWithRegisteredEmail)
                                                openDialog.value = true
                                            }

                                        }
                                }

                            }
                        },
                    )
                }
                if (isCodeTextFieldVisible.value) {
                    Button(modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3366ff)),
                        content = {
                            Text(text = "Confirm Code", textAlign = TextAlign.Center)
                        },
                        onClick = {
                            viewModel.updatePassword(
                                password = viewModel.formState.password,
                                email = viewModel.formState.email
                            )
                            dialogMessage.value =
                                context.getString(R.string.strThePasswordHasBeenUpdatedSuccessfully)
                            openDialog.value = true
                            isPasswordChange.value = true

                        })
                }
            }
        }
    }
}