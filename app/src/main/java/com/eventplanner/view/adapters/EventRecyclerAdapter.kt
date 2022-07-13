package com.eventplanner.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.eventplanner.R
import com.eventplanner.model.models.EventModel
import com.squareup.picasso.Picasso


class EventRecyclerAdapter(private val context: Context?) :
    RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder>() {
    private var items: MutableList<EventModel> = mutableListOf()
    private lateinit var onClickListener: OnClickListener

    fun setItems(items: MutableList<EventModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(position: Int, items: MutableList<EventModel>)
        fun onSettingsClick(position: Int, items: MutableList<EventModel>)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false)
        return MyViewHolder(view, onClickListener, items)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemModel = items[position]
        holder.dateTxt.text = itemModel.date
        holder.timeTxt.text = itemModel.time
        holder.weatherTxt.text = itemModel.weather
        holder.eventNameTxt.text = itemModel.eventName
        holder.descriptionTxt.text = itemModel.description
        holder.locationTxt.text = itemModel.location

        Picasso.get()
            .load(itemModel.icon)
            .into(holder.icon)
    }

    override fun getItemCount() = items.size

    class MyViewHolder(view: View, clickListener: OnClickListener, items: MutableList<EventModel>) :
        RecyclerView.ViewHolder(view) {
        val dateTxt: TextView = view.findViewById(R.id.date_txt)
        val timeTxt: TextView = view.findViewById(R.id.time_txt)
        val weatherTxt: TextView = view.findViewById(R.id.weather_txt)
        val eventNameTxt: TextView = view.findViewById(R.id.event_name_txt)
        val descriptionTxt: TextView = view.findViewById(R.id.description_txt)
        val locationTxt: TextView = view.findViewById(R.id.location_txt)
        val icon: ImageView = view.findViewById(R.id.weather_icon)

        private val updateBtn: ImageButton = view.findViewById(R.id.setting_btn)

        init {
            view.setOnClickListener {
                clickListener.onItemClick(adapterPosition, items)
            }
            updateBtn.setOnClickListener {
                clickListener.onSettingsClick(adapterPosition, items)
            }

        }
    }
}