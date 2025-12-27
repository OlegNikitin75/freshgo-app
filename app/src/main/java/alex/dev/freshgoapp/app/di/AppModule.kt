package alex.dev.freshgoapp.app.di

import alex.dev.freshgoapp.R
import alex.dev.freshgoapp.app.data.repositrory.CustomerRepoImpl
import alex.dev.freshgoapp.app.domain.repository.CustomerRepository
import alex.dev.freshgoapp.app.presentation.screens.auth.AuthViewModel
import alex.dev.freshgoapp.app.presentation.screens.auth.google.GoogleUiClient
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<CustomerRepository> { CustomerRepoImpl() }
    viewModel { AuthViewModel(get()) }
    single {
        GoogleUiClient(
            context = androidContext(),
            auth = get(),
            serverClient = androidContext().getString(R.string.server_client_id)
        )
    }
}