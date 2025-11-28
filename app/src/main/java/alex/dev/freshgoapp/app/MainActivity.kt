package alex.dev.freshgoapp.app

import alex.dev.freshgoapp.app.navigation.NavigationGraph
import alex.dev.freshgoapp.ui.theme.FreshGoAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreshGoAppTheme {
                NavigationGraph()
            }
        }
    }
}

