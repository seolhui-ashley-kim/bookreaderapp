package com.seolhui.bookreaderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.seolhui.bookreaderapp.navigation.ReaderNavigation
import com.seolhui.bookreaderapp.ui.theme.ReaderApp_Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReaderApp_Theme {
                ReaderApp()
            }
        }
    }
}

@Composable
fun ReaderApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderNavigation()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReaderApp_Theme {

    }
}
