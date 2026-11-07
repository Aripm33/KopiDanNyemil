package com.example.kopidannyemil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kopidannyemil.data.AppViewModel
import com.example.kopidannyemil.ui.theme.ErrorRed
import com.example.kopidannyemil.ui.theme.Muted
import com.example.kopidannyemil.ui.theme.Navy
import com.example.kopidannyemil.ui.theme.NavyLight

/** 1. Splash / Welcome */
@Composable
fun SplashScreen(onStart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Navy, NavyLight)))
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(128.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("☕", fontSize = 60.sp)
            }
            Spacer(Modifier.height(28.dp))
            Text(
                "Kopi dan Nyemil",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.height(8.dp))
            Text("Get a taste of your wish", color = Color(0xFFBFD3EE), fontSize = 16.sp)
        }
        Button(
            onClick = onStart,
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Navy)
        ) {
            Text("Start now", fontWeight = FontWeight.Bold)
        }
    }
}

/** 2. Sign up (pilihan Register / Login) */
@Composable
fun SignUpScreen(onRegister: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(24.dp)
    ) {
        Spacer(Modifier.height(48.dp))
        Box(
            modifier = Modifier.size(72.dp).background(Navy, CircleShape),
            contentAlignment = Alignment.Center
        ) { Text("☕", fontSize = 34.sp) }
        Spacer(Modifier.height(24.dp))
        Text("Sign up", style = MaterialTheme.typography.headlineLarge, color = Navy)
        Spacer(Modifier.height(10.dp))
        Text(
            "Join as a member & experience more fun from various meal at mesen_gube marketing app",
            color = Muted,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton("Register", onRegister)
        Spacer(Modifier.height(12.dp))
        SecondaryButton("Login", onLogin)
    }
}

@Composable
private fun AuthScaffold(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Navy)
        }
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.headlineLarge, color = Navy)
        Spacer(Modifier.height(6.dp))
        Text(subtitle, color = Muted)
        Spacer(Modifier.height(28.dp))
        content()
    }
}

/** 3. Login User */
@Composable
fun LoginScreen(vm: AppViewModel, onSuccess: () -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AuthScaffold("Login User", "Masuk dengan akunmu. Akun demo: demo / 123456", onBack) {
        AppTextField(username, { username = it; error = null }, "Username")
        Spacer(Modifier.height(14.dp))
        AppTextField(password, { password = it; error = null }, "Password", isPassword = true)
        error?.let {
            Spacer(Modifier.height(10.dp))
            Text(it, color = ErrorRed, fontSize = 13.sp)
        }
        Spacer(Modifier.height(28.dp))
        PrimaryButton("Login", {
            error = vm.login(username, password)
            if (error == null) onSuccess()
        })
    }
}

/** 4. Register */
@Composable
fun RegisterScreen(vm: AppViewModel, onSuccess: () -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AuthScaffold("Register", "Buat akun baru untuk mulai memesan", onBack) {
        AppTextField(name, { name = it; error = null }, "Nama lengkap")
        Spacer(Modifier.height(14.dp))
        AppTextField(username, { username = it; error = null }, "Username")
        Spacer(Modifier.height(14.dp))
        AppTextField(password, { password = it; error = null }, "Password (min. 6 karakter)", isPassword = true)
        error?.let {
            Spacer(Modifier.height(10.dp))
            Text(it, color = ErrorRed, fontSize = 13.sp)
        }
        Spacer(Modifier.height(28.dp))
        PrimaryButton("Register", {
            error = vm.register(name, username, password)
            if (error == null) onSuccess()
        })
    }
}
