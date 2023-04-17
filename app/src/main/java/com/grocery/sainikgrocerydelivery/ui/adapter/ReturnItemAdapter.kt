package com.grocery.sainikgrocerydelivery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.model.itemmodel.ItemModel


class ReturnItemAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ReturnItemAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var itemModelArrayList: ArrayList<ItemModel> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_return_orderitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvitemName.text = itemModelArrayList[position].itemname
            tvitemSize.text = itemModelArrayList[position].itemsize

            if (itemModelArrayList[position].isReturnNoteShown) {
                llNote.visibility = View.VISIBLE
                btnSubmit.visibility = View.VISIBLE
                ivDown.rotation = 270f
            } else {

                    llNote.visibility = View.GONE
                    btnSubmit.visibility = View.GONE
                    ivDown.rotation = 90f

            }

            cbReturned.isChecked = itemModelArrayList[position].isReturned

            cbReturned.setOnCheckedChangeListener { buttonView, isChecked ->
                itemModelArrayList[position].isReturned = isChecked

            }
            itemView.setOnClickListener {
                itemModelArrayList.forEach { item ->
                    item.isReturnNoteShown = false
                }
                itemModelArrayList[position].isReturnNoteShown = true
                notifyDataSetChanged()
            }

        }
    }

    fun updateData(mitemModelArrayList: ArrayList<ItemModel>) {
        itemModelArrayList = mitemModelArrayList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvitemName: TextView
        var tvitemSize: TextView
        var ivDown: ImageView
        var llNote: LinearLayout
        var editTextbugDescription: AppCompatEditText
        var btnSubmit: AppCompatButton
        var cbReturned: CheckBox


        init {

            tvitemName = itemView.findViewById(R.id.tvitemName)
            tvitemSize = itemView.findViewById(R.id.tvitemSize)
            ivDown = itemView.findViewById(R.id.ivDown)
            llNote = itemView.findViewById(R.id.llNote)
            editTextbugDescription = itemView.findViewById(R.id.editTextbugDescription)
            btnSubmit = itemView.findViewById(R.id.btnSubmit)
            cbReturned = itemView.findViewById(R.id.cbReturned)
        }
    }
}