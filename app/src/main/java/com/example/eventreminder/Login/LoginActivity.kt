package com.example.eventreminder.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback

import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.example.eventreminder.Home.HomeActivity
import com.example.eventreminder.R
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker


class LoginActivity : AppCompatActivity() {

    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()


        val EMAIL = "email"

        login_button.setReadPermissions(listOf(EMAIL, "public_profile"))
        checkLoginStatus()

        // Callback registration , after Registeration , goToHomeActivity :
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                goToHomeActivity()
            }

            override fun onCancel() {
            }

            override fun onError(exception: FacebookException) {
                Log.i("facebook", "error  is ${exception.message}")

            }
        })
    }

    private fun goToHomeActivity() {
        var intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        tokenTracker.stopTracking()
    }

    var tokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
            if (currentAccessToken == null) {
                Toast.makeText(this@LoginActivity, "User Logged out", Toast.LENGTH_LONG).show()
            } else
                goToHomeActivity()
        }
    }


    private fun checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            goToHomeActivity()
        }
    }

}
