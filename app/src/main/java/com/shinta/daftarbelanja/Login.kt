package com.shinta.daftarbelanja

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.ActionMode
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progress_bar)
        btnLogin.setOnClickListener(this)
        progressBar.visibility = View.GONE
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser == null){

        }
        else {
           val go = Intent(applicationContext, MainActivity::class.java)
            startActivity(go)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val respon = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "Login Anda Telah Berhasil!!",
                    Toast.LENGTH_SHORT).show()
                val go = Intent(applicationContext, MainActivity::class.java)
                startActivity(go)
                finish()
            }
            else {
                Toast.makeText(this, "Login Anda Gagal!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onClick(p0: View?) {
        val profider = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(profider)
                .build(),RC_SIGN_IN
        )
    }
}