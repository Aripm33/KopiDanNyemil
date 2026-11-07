package com.example.kopidannyemil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kopidannyemil.data.AppViewModel
import com.example.kopidannyemil.ui.screens.ConfirmationScreen
import com.example.kopidannyemil.ui.screens.LoginScreen
import com.example.kopidannyemil.ui.screens.MainScreen
import com.example.kopidannyemil.ui.screens.RegisterScreen
import com.example.kopidannyemil.ui.screens.SignUpScreen
import com.example.kopidannyemil.ui.screens.SplashScreen
import com.example.kopidannyemil.ui.theme.KopiDanNyemilTheme

object Routes {
    const val SPLASH = "splash"
    const val SIGN_UP = "signup"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAIN = "main"
    const val CONFIRMATION = "confirmation"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiDanNyemilTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun AppNavigation() {
    val nav = rememberNavController()
    val vm: AppViewModel = viewModel()

    val goHome = {
        nav.navigate(Routes.MAIN) { popUpTo(Routes.SPLASH) { inclusive = true } }
    }

    NavHost(navController = nav, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onStart = { nav.navigate(Routes.SIGN_UP) })
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onRegister = { nav.navigate(Routes.REGISTER) },
                onLogin = { nav.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(vm, onSuccess = { goHome() }, onBack = { nav.popBackStack() })
        }
        composable(Routes.REGISTER) {
            RegisterScreen(vm, onSuccess = { goHome() }, onBack = { nav.popBackStack() })
        }
        composable(Routes.MAIN) {
            MainScreen(
                vm,
                onOrderSuccess = { nav.navigate(Routes.CONFIRMATION) },
                onLogout = {
                    vm.logout()
                    nav.navigate(Routes.SIGN_UP) { popUpTo(Routes.MAIN) { inclusive = true } }
                }
            )
        }
        composable(Routes.CONFIRMATION) {
            ConfirmationScreen(vm, onOk = {
                vm.clearCart()
                nav.popBackStack(Routes.MAIN, inclusive = false)
            })
        }
    }
}
