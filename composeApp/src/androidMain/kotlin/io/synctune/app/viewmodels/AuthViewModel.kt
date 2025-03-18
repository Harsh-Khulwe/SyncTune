package io.synctune.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import io.synctune.app.data.UserRepository
import kotlinx.coroutines.launch
import io.synctune.app.data.Result

class AuthViewModel: ViewModel() {

    private val userRepository: UserRepository
    private val _authResult = MutableLiveData<Result<Boolean>>()

    val authResult: LiveData<Result<Boolean>> =_authResult

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email, password, firstName, lastName)
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signIn(email, password)
        }
    }

    fun signOut() {
        userRepository.signOut()
    }
}