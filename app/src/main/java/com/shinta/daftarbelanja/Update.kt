package com.shinta.daftarbelanja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Update : AppCompatActivity() {

    private var db: DatabaseReference? = null
    private var otent: FirebaseAuth? = null
    private var cekNM: String? = null
    private var cekJML: String? = null
    private lateinit var upd: Button
    private lateinit var ed1: EditText
    private lateinit var ed2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar!!.title = "Update Belanja"

        upd = findViewById(R.id.btn_upd)
        ed1 = findViewById(R.id.ed_1)
        ed2 = findViewById(R.id.ed_2)
        otent = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference
        data
        upd.setOnLongClickListener(object : View.OnClickListener, View.OnLongClickListener {
            override fun onClick(v: View?) {
                cekNM = ed1.getText().toString()
                cekJML = ed2.getText().toString()

                if (isEmpty(cekNM!!) || isEmpty(cekJML!!)) {
                    Toast.makeText(this@Update, "Data Tidak Boleh Kosong!!", Toast.LENGTH_SHORT).show()
                }
                else {
                    val setBRG = data_belanja()
                    setBRG.NM = ed1.getText().toString()
                    setBRG.JML = ed2.getText().toString()
                    updBRG(setBRG)
                }
            }

            override fun onLongClick(p0: View?): Boolean {
                TODO("Not yet implemented")
            }
        })

    }

    private fun updBRG(brg: data_belanja) {
        val UID = otent!!.uid
        val getkey = intent.extras!!.getString("getPrimaryKey")
        db!!.child("User")
            .child(UID!!)
            .child("DaftarBelanja")
            .setValue(brg)
            .addOnSuccessListener {
                ed1!!.setText("")
                ed2!!.setText("")
                Toast.makeText(this@Update, "Successfully update data!!", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private val data: Unit
    private get() {
        val getNM = intent.extras!!.getString("dataNama")
        val getJML = intent.extras!!.getString("dataJumlah")
        ed1!!.setText(getNM)
        ed2!!.setText(getJML)
    }
}