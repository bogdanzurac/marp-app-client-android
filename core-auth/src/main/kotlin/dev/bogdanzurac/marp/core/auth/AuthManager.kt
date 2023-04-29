package dev.bogdanzurac.marp.core.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.bogdanzurac.marp.core.auth.AuthException.AccountCreationException
import dev.bogdanzurac.marp.core.auth.AuthException.InvalidCredentialsException
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.mockNetworkDelay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class AuthManager(private val featureManager: FeatureManager) {

    private val auth: FirebaseAuth = Firebase.auth
    private val userFlow: MutableStateFlow<User?> = MutableStateFlow(auth.currentUser?.toUser())

    fun getUser(): User? = userFlow.value
    fun observeUser(): Flow<User?> = userFlow
    fun observeAuthenticatedState(): Flow<Boolean> = userFlow.map { it != null }

    suspend fun loginUser(email: String, password: String): Result<Unit> =
        runCatching {
            if (featureManager.isEnabled(MockNetworkDelayFeature))
                mockNetworkDelay("login")
            val user = auth.signInWithEmailAndPassword(email, password).await().user
            checkNotNull(user)
            logger.d("User ${user.email} logged in")
            userFlow.emit(user.toUser())
        }
            .recoverCatching {
                logger.e("Error while trying to log in user", it)
                throw InvalidCredentialsException(it)
            }

    fun signOutUser() {
        auth.signOut()
        userFlow.tryEmit(null)
        logger.d("User signed out")
    }

    suspend fun signupUser(email: String, password: String): Result<Unit> =
        runCatching {
            if (featureManager.isEnabled(MockNetworkDelayFeature))
                mockNetworkDelay("signup")
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            checkNotNull(user)
            logger.d("User ${user.email} signed up")
            userFlow.emit(user.toUser())
        }
            .recoverCatching {
                logger.e("Error while trying to signup user", it)
                throw AccountCreationException(it)
            }

    private fun FirebaseUser.toUser(): User =
        User(
            uid,
            email!!,
            displayName,
            photoUrl.toString()
        )
}