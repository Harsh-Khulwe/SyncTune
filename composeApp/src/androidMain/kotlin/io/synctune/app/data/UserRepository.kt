package io.synctune.app.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String):
             Result<Boolean>{

        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(firstName, lastName, email)
            saveUserToFirestore(user)
            Result.Success(true)
        }
        catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        }
        catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        Log.i("mytag", "successfully signed out!")
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun getCurrentUser(): Result<User> = try {
        val uid = auth.currentUser?.email

        if(uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)

            if(user != null)
                Result.Success(user)
            else
                Result.Error(Exception("User details not found!!"))
        }
        else
            Result.Error(Exception("User not authenticated!!"))
    }
    catch (e: Exception) {
        Result.Error(e)
    }
}