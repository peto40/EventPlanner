package com.eventplanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.eventplanner.databinding.ItemRecyclerViewBinding
import com.eventplanner.model.EventModel
import com.squareup.picasso.Picasso


class EventRecyclerAdapter(context: Context?) :
    RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder>() {
    private var items: MutableList<EventModel> = mutableListOf()
    private lateinit var onClickListener: OnClickListener

    fun setItems(items: MutableList<EventModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(position: Int, items: MutableList<EventModel>)
        fun onUpdateClick(position: Int, items: MutableList<EventModel>)
        fun onChooseStateClick(position: Int, items: MutableList<EventModel>, view: RadioButton)

    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            onClickListener,
            items,
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemModel = items[position]
        holder.dateTxt.text = itemModel.date
        holder.timeTxt.text = itemModel.time
        holder.weatherTxt.text = itemModel.weatherDescription
        holder.eventNameTxt.text = itemModel.eventName
        holder.descriptionTxt.text = itemModel.description
        holder.locationTxt.text = itemModel.location
        itemModel.state?.let {
            if (itemModel.state) {
                holder.state.text = "Visited"
            } else holder.state.text = "Missed"
        }


        Picasso.get()
            .load(itemModel.icon)
            .into(holder.icon)
    }

    override fun getItemCount() = items.size

    class MyViewHolder(
        clickListener: OnClickListener,
        items: MutableList<EventModel>,
        itemBinding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        val dateTxt: TextView = itemBinding.dateTxt
        val timeTxt: TextView = itemBinding.timeTxt
        val weatherTxt: TextView = itemBinding.weatherTxt
        val eventNameTxt: TextView = itemBinding.eventNameTxt
        val descriptionTxt: TextView = itemBinding.descriptionTxt
        val locationTxt: TextView = itemBinding.locationTxt
        val icon: ImageView = itemBinding.weatherIcon
        val state: TextView = itemBinding.tvState
        val visited: RadioButton = itemBinding.radioVisited
        val missed: RadioButton = itemBinding.radioMissed

        private val updateBtn: ImageButton = itemBinding.settingBtn

        init {
            itemBinding.root.setOnClickListener {
                clickListener.onItemClick(adapterPosition, items)
            }
            updateBtn.setOnClickListener {
                clickListener.onUpdateClick(adapterPosition, items)
            }
            visited.setOnClickListener {
                clickListener.onChooseStateClick(adapterPosition, items, visited)

            }
            missed.setOnClickListener {
                clickListener.onChooseStateClick(adapterPosition, items, missed)
            }

        }
    }
}