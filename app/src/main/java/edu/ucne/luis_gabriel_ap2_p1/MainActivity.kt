package edu.ucne.luis_gabriel_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.luis_gabriel_ap2_p1.ui.theme.Luis_Gabriel_AP2_P1Theme
import edu.ucne.luis_gabriel_ap2_p1.presentation.navigation.CervezaNavHost
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Luis_Gabriel_AP2_P1Theme {
                val navController = rememberNavController()
                CervezaNavHost(navHostController = navController)
            }
        }
    }
}