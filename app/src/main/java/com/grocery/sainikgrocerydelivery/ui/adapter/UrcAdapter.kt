package com.grocery.sainikgrocerydelivery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel.DataList
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcModel


class UrcAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<UrcAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var urcModelArrayList: ArrayList<DataList> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
       // this.savedCardModelArrayList = savedCardModelArrayList
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_urc, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvOrderid.text = urcModelArrayList[position].orderReferenceId
            tvAddress.text = urcModelArrayList[position].address.streetDetails+","+
                    urcModelArrayList[position].address.apartmentName+","+
                    urcModelArrayList[position].address.houseNo+","+
                    urcModelArrayList[position].address.city



            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it, urcModelArrayList[position].id)

            }

//
//            btnDetails.setOnClickListener {
//
//
//            }


        }
    }

    fun updateData(murcModelArrayList: List<DataList>){
        urcModelArrayList=murcModelArrayList as ArrayList<DataList>
        notifyDataSetChanged()
    }


    fun addData(murcModelArrayList: List<DataList>) {

        val lastIndex: Int = murcModelArrayList.size
        urcModelArrayList.addAll(murcModelArrayList)
        notifyItemRangeInserted(lastIndex, murcModelArrayList.size)

    }

    override fun getItemCount(): Int {
        return urcModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvOrderid: TextView
        var tvAddress: TextView
        var btnDetails: LinearLayout


        init {

            tvOrderid = itemView.findViewById(R.id.tvOrderid)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            btnDetails = itemView.findViewById(R.id.btnDetails)
        }
    }
}