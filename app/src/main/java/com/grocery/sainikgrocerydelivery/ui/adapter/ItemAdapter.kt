package com.grocery.sainikgrocerydelivery.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel.OrderHistory
import com.squareup.picasso.Picasso


class ItemAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var itemModelArrayList: ArrayList<OrderHistory> = arrayListOf()
    private var imageURL: String=""
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_orderitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            tvitemName.text = itemModelArrayList[position].product.name
//            tvitemSize.text = itemModelArrayList[position].productPackSize.size
            tvitemSize.text = "12KG"
            Picasso.get()
                .load(imageURL+"/"+itemModelArrayList[position].product.image)
                .error(R.drawable.orderitem)
                .placeholder(R.drawable.orderitem)
                .into(holder.itemImg)

        }
    }

    fun updateData(mitemModelArrayList: List<OrderHistory>, mImageURL:String) {
        itemModelArrayList = mitemModelArrayList as ArrayList<OrderHistory>
        imageURL=mImageURL
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvitemName: TextView
        var tvitemSize: TextView
        var itemImg: ImageView


        init {

            tvitemName = itemView.findViewById(R.id.tvitemName)
            tvitemSize = itemView.findViewById(R.id.tvitemSize)
            itemImg = itemView.findViewById(R.id.itemImg)
        }
    }
}