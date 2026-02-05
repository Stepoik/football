package com.stepoik.footballstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.retainedComponent
import com.stepoik.footballstats.ui.features.root.Root
import com.stepoik.footballstats.ui.features.root.RootComponent
import com.stepoik.footballstats.ui.theme.FootballStatsTheme
import org.koin.mp.KoinPlatform.getKoin

class MainActivity : ComponentActivity() {
    private val rootComponentFactory = getKoin().get<RootComponent.Factory>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = retainedComponent {
            rootComponentFactory.create(defaultComponentContext())
        }
        setContent {
            FootballStatsTheme {
                Root(rootComponent)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FootballStatsTheme {
        Greeting("Android")
    }
}