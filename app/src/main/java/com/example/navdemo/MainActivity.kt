package com.example.navdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.navdemo.ui.theme.NavDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavDemoTheme {
                Scaffold { innerPadding ->
                    NavigationExampleApp(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController){
    var text by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text(
            text = "This is home screen",
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it }
        )
        Button(
            onClick = { navController.navigate("second/$text") },
            enabled = text.isNotEmpty()
            )
        {
            Text(text = "To second screen")
        }
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    parameter: String?){
    Column(modifier = modifier) {
        Text(
            text = "This is second screen",
        )
        Text(
            text = "Parameter from home screen: $parameter",
        )
        Button(onClick = { navController.navigate("home") }
        ) {
            Text(text = "To home screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NavDemoTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}

@Composable
fun NavigationExampleApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            HomeScreen(modifier, navController)
        }
        composable(route = "second/{parameter}") {
            SecondScreen(modifier, navController, it.arguments?.getString("parameter"))
        }
    }
}
