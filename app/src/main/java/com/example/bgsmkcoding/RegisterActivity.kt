package com.example.bgsmkcoding

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        progressDialog = ProgressDialog(this)

        btnSignUp.setOnClickListener { ValidasiRegister() }
        btnLogin.setOnClickListener { gotoLogin() }
    }

    private fun ValidasiRegister() {

        if (validation()) {
            val json = JSONObject()
            json.put("username", et_username.text.toString())
            json.put("password", et_password.text.toString())

            progressDialog!!.setMessage("Proses Registrasi")
            showDialog()

            ApiService.loginApiCall().doRegister(
                RegisterRequest(
                    et_username.text.toString(),
                    et_password.text.toString()
                )
            ).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {

                    Log.d("Respojhnse::::", response.body().toString())
                    val loginResponse :  RegisterResponse
                    loginResponse = response.body()!!
                    if (loginResponse.status.equals("true") || loginResponse.message.equals("Successfully registered!")){
                        hideDialog()
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    }else{
                        hideDialog()
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    hideDialog()
                }

            })
        }
    }

    private fun gotoLogin() {
        onBackPressed()
    }

    fun validation(): Boolean {
        var value = true

        val username = et_username.text.toString().trim()
        val password = et_password.text.toString().trim()

        if (username.isEmpty()) {
            et_username.error = "Username required"
            et_username.requestFocus()
            value = false
        }


        if (password.isEmpty()) {
            et_password.error = "Password required"
            et_password.requestFocus()
            value = false
        }

        return value;
    }

    private fun showDialog() {
        if (!progressDialog!!.isShowing) progressDialog!!.show()
    }

    private fun hideDialog() {
        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
    }
}


