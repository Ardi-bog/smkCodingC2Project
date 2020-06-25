package com.example.bgsmkcoding

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build.MODEL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Method
import java.math.BigInteger

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var hasilmd5: String? = null
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)
        progress.visibility = View.GONE
        login.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()

        if(auth!!.currentUser == null){
        }else{
            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val id_user = idUser
        if (id_user != "null") {
            gotoCourseActivity()
        }

        btnLogin.setOnClickListener { btnMD5() }
        btnRegister.setOnClickListener { gotoRegister() }

    }

    private fun TOAST() {
        Toast.makeText(applicationContext, "Fitur Belum Dibuat",Toast.LENGTH_SHORT).show()
    }


    private fun gotoRegister() {
        startActivity(Intent (this, RegisterActivity :: class.java))
    }

    fun btnMD5() {
        val md5input = et_password!!.text.toString().toByteArray()
        var md5Data: BigInteger? = null
        try {
            md5Data = BigInteger(1, encryptMd5.encryptMd5(md5input))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var md5Str = md5Data!!.toString(16)
        if (md5Str.length < 32) {
            md5Str = "0$md5Str"
        }
        hasilmd5 = md5Str

        if(et_email!!.text.toString().isEmpty()){
            et_email.error = "Harap isi email anda"
        } else if(et_password!!.text.toString().isEmpty()){
            et_password.error = "Harap isi password"
        }else {
            login()
        }
    }

    private fun login() {
        progressDialog!!.setMessage("Proses Login")
        showDialog()

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://bogelardi.000webhostapp.com/login.php",
            Response.Listener { response ->
                if (response.contains("success")) {
                    hideDialog()
                    val id = response.split(";").toTypedArray()[1]
                    val username = response.split(";").toTypedArray()[2]
                    val password = response.split(";").toTypedArray()[3]
                    setPreference(id, username, password)
                    gotoCourseActivity()
                } else {
                    hideDialog()
                }
            },
            Response.ErrorListener {
                hideDialog()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = et_email.text.toString()
                params["password"] = hasilmd5!!
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun gotoCourseActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun showDialog() {
        if (!progressDialog!!.isShowing) progressDialog!!.show()
    }

    private fun hideDialog() {
        if (progressDialog!!.isShowing) progressDialog!!.dismiss()
    }

    fun setPreference(id: String?, email: String?, password: String? ) {
        val mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = mSettings.edit()
        editor.putString("id_user", id)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private val idUser: String
        private get() {
            val preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
            return preferences.getString("id_user", "null")
        }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
        ) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Login Berhasil" , Toast.LENGTH_SHORT).show()
                intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                progress.visibility = View.GONE
                Toast.makeText(this, "Login Dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

        override fun onClick(v: View?) {
        startActivityForResult(
            AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
            .setIsSmartLockEnabled(false)
            .build(),
            RC_SIGN_IN)

        progress.visibility = View.VISIBLE
    }

}
