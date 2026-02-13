package alex.dev.freshgoapp.core.data.auth

import android.app.Activity
import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleUiClient(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val serverClient: String
) {
    private val credManager by lazy { CredentialManager.create(context) }
    suspend fun signInWithGoogle(activity: Activity): AuthResult {
        val googleOptions = GetGoogleIdOption.Builder().setServerClientId(serverClient)
            .setFilterByAuthorizedAccounts(false).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleOptions).build()
        val result = credManager.getCredential(activity, request)
        val googleCred = when (val cred = result.credential) {
            is CustomCredential -> {
                if (cred.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    GoogleIdTokenCredential.createFrom(cred.data)
                } else error("Unsupported credential type: ${cred.type}")
            }

            else -> error("Unsupported credential class: ${result.credential::class.java.name}")
        }
        val idToken = googleCred.idToken
        val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(firebaseCred).await()
    }

    suspend fun guestSignIn(): AuthResult =
        auth.signInAnonymously().await()

    suspend fun signOut() {
        auth.signOut()
        runCatching { credManager.clearCredentialState(ClearCredentialStateRequest()) }
    }

    val currentUser get() = auth.currentUser
}