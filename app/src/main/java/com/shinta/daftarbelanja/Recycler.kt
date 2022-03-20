package com.shinta.daftarbelanja

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class Recycler(
    private var listBrg: ArrayList<data_belanja>,
    context: Context
) :
        RecyclerView.Adapter<Recycler.ViewHolder>(){
            private var context: Context

            inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val NamaBrg: TextView
                val JumlahBrg: TextView
                val ListItem: LinearLayout

                init {
                    NamaBrg = itemView.findViewById(R.id.new_barang)
                    JumlahBrg = itemView.findViewById(R.id.new_jumlah)
                    ListItem = itemView.findViewById(R.id.list_view_brg)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false)
        return ViewHolder(V)
    }

    @SuppressLint("SetTextI18n", "RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val NmBrg: String? = listBrg.get(position).NM
        val JmlBrg: String? = listBrg.get(position).JML

        holder.NamaBrg.text = "Nama Barang   : $NmBrg"
        holder.JumlahBrg.text = "Jumlah Barang : $JmlBrg"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener { view ->
                    val ac = arrayOf("Update", "Delete")
                    val ale: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    ale.setItems(ac, DialogInterface.OnClickListener{ dialog, i ->
                        when (i) {
                            0 -> {
                                val bund = Bundle()
                                bund.putString("dataNama", listBrg[position].NM)
                                bund.putString("dataJumlah", listBrg[position].JML)
                                bund.putString("getPrimaryKey", listBrg[position].key)
                                val up = Intent(view.context, Update::class.java)
                                up.putExtras(bund)
                                context.startActivity(up)
                            }
                            1 -> {
                                listener?.onDeleteData(listBrg.get(position), position)
                            }
                        }
                    })
                    ale.create()
                    ale.show()
                    true
                }
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listBrg.size
    }

    init {
        this.context = context
    }
        interface dataListener{
            fun onDeleteData(brg: data_belanja?, position: Int)
        }
    var listener:  dataListener? = null
    fun Recycler(listBrg: ArrayList<data_belanja>?, context: Context?) {
        this.listBrg = listBrg!!
        this.context = context!!
        listener = context as DaftarBelanja?
    }
        }