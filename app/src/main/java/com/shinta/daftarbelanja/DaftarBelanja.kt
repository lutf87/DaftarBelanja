package com.shinta.daftarbelanja

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DaftarBelanja : AppCompatActivity(), Recycler.dataListener {

    private var recycler: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutMn: RecyclerView.LayoutManager? = null

    val db = FirebaseDatabase.getInstance()
    private var dbShoping = ArrayList<data_belanja>()
    private var otentikasi: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_belanja)
        recycler = findViewById(R.id.detail_belanja)
        supportActionBar!!.title = "Daftar Belanja"
        otentikasi = FirebaseAuth.getInstance()
        RecyclerView()
        GetData()
    }

    private fun GetData() {
        Toast.makeText(applicationContext, "Please Wait...", Toast.LENGTH_SHORT).show()
        val getUserID: String = otentikasi?.getCurrentUser()?.getUid().toString()
        val getReference = db.getReference()
        getReference.child("User").child(getUserID).child("DaftarBelanja")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (senep in snapshot.children) {
                            val belanja = senep.getValue(data_belanja::class.java)
                            belanja?.key = senep.key
                            dbShoping.add(belanja!!)
                        }

                        adapter = Recycler(dbShoping, this@DaftarBelanja)
                        recycler?.adapter = adapter
                        (adapter as Recycler).notifyDataSetChanged()
                        Toast.makeText(applicationContext, "Data Received!!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failed to process data", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("DaftarBelanja", error.details + " " + error.message)
                }
            })
    }

    private fun RecyclerView() {
        layoutMn = LinearLayoutManager(this)
        recycler?.layoutManager = layoutMn
        recycler?.setHasFixedSize(true)
        val itemDec = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDec.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.line)!!)
        recycler?.addItemDecoration(itemDec)
    }

    override fun onDeleteData(brg: data_belanja?, position: Int) {
        val getUID: String = otentikasi?.getCurrentUser()?.getUid().toString()
        val getRef = db.getReference()
        val getKey = intent.extras!!.getString("getprimaryKey")
        if (getRef != null) {
            getRef.child("User")
                .child(getUID)
                .child("DaftarBelanja")
                .child(getKey!!)
                .removeValue()
                .addOnSuccessListener { 
                    Toast.makeText(this@DaftarBelanja, "Successfully Deleted Data!!", Toast.LENGTH_SHORT).show()
                    finish()
                }


        }
    }
}