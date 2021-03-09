package ru.startandroid.develop.financemanagment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.registration.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.models.User
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder

class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        signUpBut.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

        registrButton.setOnClickListener {
            MainScope().launch(Dispatchers.Main) {
                val name = editTextTextPersonName.text.toString().trim()
                val email = editTextTextEmailAddress2.text.toString().trim()
                val password = editTextTextPassword2.text.toString().trim()

                if (name.isEmpty()) {
                    editTextTextPersonName.error = "Поле не должно быть пустым"
                    editTextTextPersonName.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Поле не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                if (email.isEmpty()) {
                    editTextTextEmailAddress2.error = "Поле не должно быть пустым"
                    editTextTextEmailAddress2.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Поле не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                if (password.isEmpty()) {
                    editTextTextPassword2.error = "Поле не должно быть пустым"
                    editTextTextPassword2.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Поле не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
                register(name, email, password)
            }
        }
    }


    private  fun register(name: String, email: String, password: String) {
        val retrofitInstance = RetrofitBuilder.getInstance()
        val registerInfo = User(name, email, password)
        retrofitInstance.register(registerInfo).enqueue(object : Callback<ResponseBody>{

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful || response.code() == 201){
                    Toast.makeText(applicationContext, "Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, AuthorizationActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "Ошибкаааааа регистрации", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(applicationContext,"Ошибка регистрации", Toast.LENGTH_SHORT).show()
                t.message
            }



        })
    }


}


//override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//    if(response.isSuccessful || response.code() == 201){
//        Toast.makeText(applicationContext,"Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show()
//        val intent = Intent(applicationContext, AuthorizationActivity::class.java)
//        startActivity(intent)
//    }
//    else{
//        Toast.makeText(applicationContext,"Ошибка регистрации", Toast.LENGTH_SHORT).show()
//    }
//}
//override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//    Toast.makeText(applicationContext, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
//    t.message
//}