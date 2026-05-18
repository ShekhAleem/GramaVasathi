package com.gramavasathi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gramavasathi.app.ui.screens.GramaVasathiApp
import com.gramavasathi.app.ui.theme.GramaVasathiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GramaVasathiTheme {
                GramaVasathiApp()
            }
        }
    }
}
