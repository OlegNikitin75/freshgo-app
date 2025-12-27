package alex.dev.freshgoapp.app

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.app.presentation.navigation.NavigationGraph
import alex.dev.freshgoapp.ui.theme.FreshGoAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.res.ResourcesCompat

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



