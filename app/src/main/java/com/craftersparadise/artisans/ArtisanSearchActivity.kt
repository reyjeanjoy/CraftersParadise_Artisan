package com.craftersparadise.artisans

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArtisanSearchActivity : AppCompatActivity() {

    private lateinit var ratingsRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_search) // Your activity's layout

        ratingsRecyclerView = findViewById(R.id.ratingsRecyclerView)
        productRecyclerView = findViewById(R.id.productRecyclerView)

        setupRatingsRecyclerView()
        setupProductRecyclerView()
    }

    private fun setupRatingsRecyclerView() {
        val ratingsAdapter = RatingsAdapter(this, getRatings()) { rating ->
            // Handle item click, navigate to ArtisanViewShopActivity
            val intent = Intent(this, ArtisanViewShopActivity::class.java)
            intent.putExtra("shopName", rating.name) // Pass shop name to the new activity
            startActivity(intent)
        }

        ratingsRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ratingsRecyclerView.adapter = ratingsAdapter
    }

    private fun setupProductRecyclerView() {
        val productAdapter = ProductAdapter(getProducts())
        productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        productRecyclerView.adapter = productAdapter
    }

    private fun getRatings(): List<Ratings> {
        return listOf(
            Ratings("Netibo", R.drawable.sample_image1, 4.5f),
            Ratings("Christian Rattan", R.drawable.sample_image1, 3.0f),
            Ratings("Sandayong", R.drawable.sample_image1, 5.0f)
        )
    }

    private fun getProducts(): List<Product> {
        return listOf(
            Product("Bag", R.drawable.sample_post),
            Product("Dining Table", R.drawable.sample_post),
            Product("Modern Chair", R.drawable.sample_post),
            Product("Basket", R.drawable.sample_post),
            Product("Modern Sofa", R.drawable.sample_post),
            Product("Classic Chair", R.drawable.sample_post),
            Product("Table Design", R.drawable.sample_post),
            Product("Plant Stand", R.drawable.sample_post),
            Product("Classic Bag", R.drawable.sample_post)
        )
    }
}
