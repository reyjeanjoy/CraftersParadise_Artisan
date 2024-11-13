package com.craftersparadise.artisans

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ShopProfile(val name: String, val imageResId: Int, val location: String)

class ShopProfilesAdapter(
    private val context: Context,
    private val shopProfiles: List<ShopProfile>
) : RecyclerView.Adapter<ShopProfilesAdapter.ShopProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopProfileViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.shop_profile_item, parent, false)
        return ShopProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopProfileViewHolder, position: Int) {
        val profile = shopProfiles[position]
        holder.nameTextView.text = profile.name
        holder.imageView.setImageResource(profile.imageResId)

        // Set the click listener for the item
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArtisanViewShopActivity::class.java).apply {
                putExtra("shopName", profile.name)
                putExtra("shopLocation", profile.location)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = shopProfiles.size

    inner class ShopProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.item_shop_name)
        val imageView: ImageView = itemView.findViewById(R.id.item_shop_profile)
    }
}
