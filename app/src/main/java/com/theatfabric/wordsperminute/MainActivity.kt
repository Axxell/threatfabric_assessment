package com.theatfabric.wordsperminute

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.theatfabric.wordsperminute.foundation.ui.theme.MyAppTheme
import com.theatfabric.wordsperminute.navigation.ApplicationNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                ApplicationNavHost(navController)
            }
        }
    }
}