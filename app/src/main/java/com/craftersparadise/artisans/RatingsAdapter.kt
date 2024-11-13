package com.craftersparadise.artisans

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Ratings(val name: String, val imageResId: Int, val rating: Float)
class RatingsAdapter(
    private val context: Context,
    private val ratings: List<Ratings>,
    private val onItemClick: (Ratings) -> Unit
) : RecyclerView.Adapter<RatingsAdapter.RatingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rating, parent, false)
        return RatingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RatingsViewHolder, position: Int) {
        val rating = ratings[position]
        holder.bind(rating)
        holder.itemView.setOnClickListener { onItemClick(rating) }
    }

    override fun getItemCount(): Int = ratings.size

    inner class RatingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(rating: Ratings) {
            // Bind your rating data to the views
            itemView.findViewById<TextView>(R.id.rating_item_name).text = rating.name
            itemView.findViewById<ImageView>(R.id.rating_item_image).setImageResource(rating.imageResId)
            itemView.findViewById<RatingBar>(R.id.item_rating).rating = rating.rating
        }
    }
}
