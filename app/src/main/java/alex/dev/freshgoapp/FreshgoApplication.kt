package alex.dev.freshgoapp

import alex.dev.freshgoapp.core.di.appModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FreshgoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FreshgoApplication)
            modules(appModule)
        }
    }
}