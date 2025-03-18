package io.synctune.app.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.synctune.app.data.Result
import io.synctune.app.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navigateToSignup: () -> Unit,
    onSignInSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPassVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val result by authViewModel.authResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Enter email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email address",
                    modifier = Modifier.padding(start = 8.dp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text("Enter password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = "Password",
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            trailingIcon = {
                val image = if(isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val desc = if(isPassVisible) "Password is visible" else "Password is not visible"

                IconButton(onClick = { isPassVisible = !isPassVisible }) {
                    Icon(imageVector = image, contentDescription = desc)
                }
            },
            visualTransformation = if(isPassVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Fill all the fields!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(
                        context,
                        "Enter email with valid syntax..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
//                    authViewModel.isLoading.value = true
                    authViewModel.signIn(email, password)
                    email = ""
                    password = ""
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp)
        ) {
            Text(text = "Sign in")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Don't have an account?")
        Text(
            text = "Sign Up",
            color = Color.Blue,
            modifier = Modifier.clickable { navigateToSignup() }
        )
    }
    LaunchedEffect(result) {
        when (result) {
            is Result.Success -> { onSignInSuccess() }
            is Result.Error -> { }
            else -> { null }
        }
    }
}