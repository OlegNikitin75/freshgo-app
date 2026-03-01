package alex.dev.freshgoapp.core.di

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.core.data.auth.GoogleUiClient
import alex.dev.freshgoapp.core.data.repository.CustomerRepository
import alex.dev.freshgoapp.core.data.repositroryImpl.CustomerRepoImpl
import alex.dev.freshgoapp.feature.screens.auth.AuthViewModel
import alex.dev.freshgoapp.feature.screens.home.HomeViewModel
import alex.dev.freshgoapp.feature.screens.profile.ProfileScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<CustomerRepository> { CustomerRepoImpl() }
    viewModel {
        AuthViewModel(
            get(),
        )
    }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileScreenViewModel(get()) }
    single {
        GoogleUiClient(
            context = androidContext(),
            auth = get(),
            serverClient = androidContext().getString(R.string.server_client_id)
        )
    }
}