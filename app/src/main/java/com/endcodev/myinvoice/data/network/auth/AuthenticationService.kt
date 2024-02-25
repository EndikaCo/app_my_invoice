package com.endcodev.myinvoice.data.network.auth

import android.util.Log
import com.endcodev.myinvoice.domain.utils.App
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthError (val error : Int){
    object NoError : AuthError(error = 0)
    object MailNoVerification : AuthError(error = 102)
    object ErrorMailOrPass : AuthError(error = 103)
    object ErrorCreatingAccount : AuthError(error = 105) // Unable to send verification email
}

@Singleton
class AuthenticationService @Inject constructor(
    private val firebase: FirebaseClient
) {
    fun createUser(email: String, pass: String, onCreateUser : (Int) -> Unit){
        val auth = firebase.auth

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                sendMailVerification()
                onCreateUser(AuthError.NoError.error)
            } else if (it.isCanceled) {
                onCreateUser(AuthError.ErrorCreatingAccount.error)
            } else {
                onCreateUser(AuthError.ErrorCreatingAccount.error)
            }
        }
    }

    private fun sendMailVerification() {

        Firebase.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
             if (it.isSuccessful) {
                Log.v(App.tag, "OK: sendEmailVerification:Success")
            } else {
                Log.e(App.tag, "Error: sendEmailVerification:fail")
            }
        }
    }

    fun mailPassLogin(loginMail: String, loginPass: String, completionHandler: (Int) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(loginMail, loginPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!Firebase.auth.currentUser?.isEmailVerified!!) {
                        completionHandler(AuthError.MailNoVerification.error)
                    } else {
                        completionHandler(AuthError.NoError.error)
                    }
                } else if (task.isCanceled || task.isComplete) {
                    completionHandler(AuthError.ErrorMailOrPass.error)
                }
            }
    }

    fun disconnectUser() {
        Firebase.auth.signOut()
    }
}