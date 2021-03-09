package ru.startandroid.develop.financemanagment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.authorization.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.models.LoginRequest
import ru.startandroid.develop.financemanagment.models.TokenResponse
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization)

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            MainScope().launch(Dispatchers.Main) {
                val username = editTextTextUserName.text.toString().trim()
                val password = editTextTextPassword.text.toString().trim()

                if (username.isEmpty()) {
                    editTextTextUserName.error = "Поле не должно быть пустым"
                    editTextTextUserName.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Поле не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                if (password.isEmpty()) {
                    editTextTextPassword.error = "Поле не должно быть пустым"
                    editTextTextPassword.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Поле не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                signIn(username, password)

            }
        }
        signIn("Ramzan","Ramzan!1")

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun signIn(name: String, password: String) {
        val retIn = RetrofitBuilder.getInstance()
        val signInInfo = LoginRequest(name, password)
        retIn.signIn(signInInfo).enqueue(object : retrofit2.Callback<TokenResponse> {
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                RetrofitBuilder.setToken(response.body()?.token ?: "")
                if (response.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }


        })
    }


}




