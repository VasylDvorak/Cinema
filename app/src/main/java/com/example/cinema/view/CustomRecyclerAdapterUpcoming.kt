package com.example.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class CustomRecyclerAdapterUpcoming(
    private val title: List<String>,
    private val year: List<String>,
    private val picture: List<Int>,
    private val listener: OnItemClickListenerUpcoming
) : RecyclerView.Adapter<CustomRecyclerAdapterUpcoming.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val upcoming_title_movie_text: TextView = itemView.findViewById(R.id.upcoming_title_movie)
        val upcoming_year_movie_text: TextView = itemView.findViewById(R.id.upcoming_year_movie)
        val upcoming_banner_picture: ImageView = itemView.findViewById(R.id.upcoming_banner)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClickUpcoming(position)
            }
        }
    }

    interface OnItemClickListenerUpcoming {
        fun onItemClickUpcoming(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.upcoming_title_movie_text.text = title[position]
        holder.upcoming_year_movie_text.text = year[position]
        holder.upcoming_banner_picture.setImageResource(picture[position])
    }

    override fun getItemCount() = title.size
}