package com.example.pistalibreandroid.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pistalibreandroid.R
import com.example.pistalibreandroid.model.ResponseError
import com.example.pistalibreandroid.model.ResponseLoading
import com.example.pistalibreandroid.model.ResponseState
import com.example.pistalibreandroid.ui.navigation.Navigation
import com.example.pistalibreandroid.ui.navigation.NavigationController
import com.example.pistalibreandroid.ui.theme.angkorFamily

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(8.dp)
    ) {
        val state: ResponseState by loginViewModel.state.collectAsState()
        
        when (state) {
            is ResponseError -> {
                Text(text = (state as ResponseError).msg)
            }
            is ResponseLoading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)){
                    Text(text = "Loading")
                }
            }
            else -> {
                Body(Modifier.align(Alignment.Center), loginViewModel)
                Footer(Modifier.align(Alignment.BottomCenter))
            }
        }
    }

}

@Composable
fun Footer(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(24.dp))
        SingUp()
        Spacer(modifier = Modifier.size(24.dp))

    }
}

@Composable
fun SingUp() {
    val colorVerde = colorResource(id = R.color.verdeApp)
    val navController = NavigationController.controller()
    
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Text(text = "¿No tienes cuenta?", fontSize = 12.sp, color = Color.White)
        Text(text = "Crear una cuenta",
            Modifier
                .padding(horizontal = 8.dp)
                .clickable { navController.navigate(Navigation.SIGN_UP_ROUTE) }, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorVerde)
    }
}

@Composable
fun Body(modifier: Modifier, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.collectAsState()
    val password: String by loginViewModel.password.collectAsState()
    val isLoginEnable: Boolean by loginViewModel.isLoginEnable.collectAsState()


    Column(modifier = modifier) {
        TextLogin(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(48.dp))
        Email(email) {
            loginViewModel.onLoginChanged(email= it, password = password)
        }
        Spacer(modifier = Modifier.size(4.dp))
        Password(password) {
            loginViewModel.onLoginChanged(email = email, password = it)
        }
        Spacer(modifier = Modifier.size(8.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(isLoginEnable, loginViewModel)
        Spacer(modifier = Modifier.size(48.dp))
        LoginDivider()
    }
}


@Composable
fun LoginDivider() {
    val colorGris = colorResource(id = R.color.grisclaro)
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            Modifier
                .background(colorGris)
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            color = colorGris
        )
        Divider(
            Modifier
                .background(colorGris)
                .height(1.dp)
                .weight(1f)
        )
    }
}

@Composable
fun TextLogin(modifier: Modifier) {
    val colorVerde = colorResource(id = R.color.verdeApp)
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Text(text = "Log", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = angkorFamily)
        Text(text = "In", Modifier.padding(horizontal = 8.dp), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = colorVerde, fontFamily = angkorFamily)
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, loginViewModel: LoginViewModel) {
    val colorVerde = colorResource(id = R.color.verdeApp)
    Button(
        onClick = { loginViewModel.onLoginSelected() },
        enabled = loginEnable,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorVerde,
            disabledContainerColor = Color(0x689DAC84),
            contentColor = Color.White,
            disabledContentColor = Color(0xA3CACACA)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = "Entrar", color = Color.White)

    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "¿Olvidaste la contraseña?",
        fontSize = 12.sp,
        color = Color.White,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña")},
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            val imagen = if(passwordVisibility){
                Icons.Filled.VisibilityOff
            }else{
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility}){
                Icon(imageVector = imagen, contentDescription = "mostrar contraseña")
            }
        },
        visualTransformation = if(passwordVisibility){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Correo electrónico")},
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}