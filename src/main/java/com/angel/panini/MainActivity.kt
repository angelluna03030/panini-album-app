package com.angel.panini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.angel.panini.ui.navigation.AppNavigation
import com.angel.panini.ui.theme.PaniniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaniniTheme {
                AppNavigation()
            }
        }
    }
}