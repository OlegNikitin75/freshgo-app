package alex.dev.freshgoapp.core.di

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.core.data.auth.GoogleUiClient
import alex.dev.freshgoapp.core.data.repository.CustomerRepository
import alex.dev.freshgoapp.core.data.repositroryImpl.CustomerRepoImpl
import alex.dev.freshgoapp.feature.screens.auth.AuthViewModel
import alex.dev.freshgoapp.feature.screens.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<CustomerRepository> { CustomerRepoImpl() }
    viewModel {
        AuthViewModel(get(),
        )
    }
    viewModel { HomeViewModel(get()) }
    single {
        GoogleUiClient(
            context = androidContext(),
            auth = get(),
            serverClient = androidContext().getString(R.string.server_client_id)
        )
    }
}