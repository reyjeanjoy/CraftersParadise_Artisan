package com.craftersparadise.artisans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class displayProduct(val name: String, val imageResId: Int)

class DisplayProductAdapter(private var displayProductList: MutableList<displayProduct>) :
    RecyclerView.Adapter<DisplayProductAdapter.DisplayProductViewHolder>() {

    class DisplayProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val displayProductImage: ImageView = itemView.findViewById(R.id.displayProductImage)
        val displayProductName: TextView = itemView.findViewById(R.id.displayProductName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_product, parent, false)
        return DisplayProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisplayProductViewHolder, position: Int) {
        val display = displayProductList[position]
        holder.displayProductName.text = display.name
        holder.displayProductImage.setImageResource(display.imageResId)
    }

    override fun getItemCount() = displayProductList.size

    fun updateProductList(newProducts: List<displayProduct>) {
        displayProductList.clear()
        displayProductList.addAll(newProducts)
        notifyDataSetChanged()
    }
}
