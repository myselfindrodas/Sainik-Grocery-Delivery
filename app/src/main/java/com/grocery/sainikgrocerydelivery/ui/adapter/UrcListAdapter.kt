package com.grocery.sainikgrocerydelivery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse.Urc
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences


class UrcListAdapter(
    ctx: Context,
    urcModelArrayList: ArrayList<Urc>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<UrcListAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var urcModelArrayList: ArrayList<Urc>
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.urcModelArrayList = urcModelArrayList
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(position: Int, view: View,urcModelArrayList: ArrayList<Urc> )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_urcitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvUrcName.text = urcModelArrayList[position].name

            rbUrc.isChecked= urcModelArrayList[position].isSelected

            btnUrc.setOnClickListener {
                onItemClickListener.onClick(position, it, urcModelArrayList)
                Shared_Preferences.setUrcid(urcModelArrayList[position].id.toString())
                Shared_Preferences.seturcname(urcModelArrayList[position].name)
                Shared_Preferences.setaddress(urcModelArrayList[position].address)


            }



        }
    }

    fun updateData(mUrcModelArrayList: List<Urc>){
        urcModelArrayList= mUrcModelArrayList as ArrayList<Urc>
        notifyDataSetChanged()
    }
    fun getURCList():List<Urc> = urcModelArrayList

    override fun getItemCount(): Int {
        return urcModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvUrcName: TextView
        var rbUrc: RadioButton
        var btnUrc: LinearLayout
        var count = 0

        init {

            tvUrcName = itemView.findViewById(R.id.tvUrcname)
            rbUrc = itemView.findViewById(R.id.rbUrc)
            btnUrc = itemView.findViewById(R.id.btnUrc)
        }
    }
}