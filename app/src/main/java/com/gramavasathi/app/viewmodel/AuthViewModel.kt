package com.gramavasathi.app.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.gramavasathi.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()
    val currentUser = repository.currentUser

    suspend fun signInWithCredential(credential: AuthCredential): Result<Unit> {
        return try {
            FirebaseAuth.getInstance().signInWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        repository.signOut()
    }
}
