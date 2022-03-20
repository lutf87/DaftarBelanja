package com.shinta.daftarbelanja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var edNama: EditText
    private lateinit var edJumlah: EditText
    private lateinit var btnReset:  Button
    private lateinit var btnSave: Button
    private lateinit var btnShow: Button
    private lateinit var btnLogout: Button

    private var otentikasi: FirebaseAuth? = null
    private var RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edNama = findViewById(R.id.nm_barang)
        edJumlah = findViewById(R.id.jml_barang)
        btnReset = findViewById(R.id.btn_reset)
        btnSave = findViewById(R.id.btn_save)
        btnShow = findViewById(R.id.btn_show)
        btnLogout = findViewById(R.id.btn_logout)

        btnSave.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.getId()){
            R.id.btn_reset -> {
                edNama.setText("")
                edJumlah.setText("")
            }
            R.id.btn_save -> {
                val getUser = otentikasi!!.currentUser!!.uid
                val dbUser = FirebaseDatabase.getInstance()

                val getNm: String = edNama.getText().toString()
                val getJml: String = edJumlah.getText().toString()

                val getRef: DatabaseReference
                getRef = dbUser.reference

                if (isEmpty(getNm) || isEmpty(getJml)) {
                    Toast.makeText(this, "Data tidak Boleh Kosong!!", Toast.LENGTH_SHORT).show()
                }
                else {
                    getRef.child("User").child(getUser).child("DaftarBelanja").push()
                        .setValue(data_belanja(getNm, getJml))
                        .addOnCompleteListener(this){
                            edNama.setText("")
                            edJumlah.setText("")
                            Toast.makeText(this@MainActivity, "Data Telah Tersimpan!!",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.btn_show -> {
                startActivity(Intent(this@MainActivity, DaftarBelanja::class.java))
            }
            R.id.btn_logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object: OnCompleteListener<Void>{
                        override fun onComplete(v: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Anda Telah Logout!",
                            Toast.LENGTH_SHORT).show()
                            val out = Intent(application, Login::class.java)
                            startActivity(out)
                            finish()
                        }
                    })
            }
        }
    }
}