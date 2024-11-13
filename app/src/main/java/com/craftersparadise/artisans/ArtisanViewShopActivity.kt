package com.craftersparadise.artisans

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ArtisanViewShopActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var displayProductAdapter: DisplayProductAdapter
    private lateinit var sellProductButton: Button
    private lateinit var categoryButtons: HorizontalScrollView
    private lateinit var buttonChairs: Button
    private lateinit var buttonSofas: Button
    private lateinit var buttonBaskets: Button
    private lateinit var buttonTables: Button
    private lateinit var buttonPlantStands: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_view_shop)

        recyclerView = findViewById(R.id.displayRecyclerView)
        sellProductButton = findViewById(R.id.sellProductButton)
        categoryButtons = findViewById(R.id.categoryButtons)
        buttonChairs = findViewById(R.id.display_buttonChairs)
        buttonSofas = findViewById(R.id.display_buttonSofas)
        buttonBaskets = findViewById(R.id.display_buttonBaskets)
        buttonTables = findViewById(R.id.display_buttonTables)
        buttonPlantStands = findViewById(R.id.display_buttonPlantStands)


        // Retrieve shop details from Intent
        val shopName = intent.getStringExtra("shopName") ?: "Unknown Shop"
        val shopLocation = intent.getStringExtra("shopLocation") ?: "Unknown Location"

        // Set shop details in views
        val shopTitle = findViewById<TextView>(R.id.display_shopname)
        val shopLocationView = findViewById<TextView>(R.id.display_shoplocation)
        shopTitle.text = shopName
        shopLocationView.text = shopLocation

        // Setup RecyclerView with products
        val initialProductList = getDisplayProducts()  // Initial list
        displayProductAdapter = DisplayProductAdapter(initialProductList.toMutableList())
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = displayProductAdapter

        // Set up button click listeners
        buttonChairs.setOnClickListener { updateProductList("Chairs") }
        buttonSofas.setOnClickListener { updateProductList("Sofas") }
        buttonBaskets.setOnClickListener { updateProductList("Baskets") }
        buttonTables.setOnClickListener { updateProductList("Tables") }
        buttonPlantStands.setOnClickListener { updateProductList("Plant Stands") }

        sellProductButton.setOnClickListener {
            val intent = Intent(this, ArtisanSellProductActivity::class.java)
            intent.putExtra("shopName", shopName)
            intent.putExtra("shopLocation", shopLocation)
            startActivity(intent)
        }
    }

    // Method to get initial products
    private fun getDisplayProducts(category: String? = null): List<displayProduct> {
        return when (category) {
            "Chairs" -> listOf(
                displayProduct("Classic Chair", R.drawable.classic_chair),
                displayProduct("Modern Chair", R.drawable.modern_chair),
                displayProduct("Vintage Chair", R.drawable.vintage_chair),
                displayProduct("Luxury Chair", R.drawable.luxury_chair)
            )
            "Sofas" -> listOf(
                displayProduct("Comfort Sofa", R.drawable.comfort_sofa),
                displayProduct("Luxury Sofa", R.drawable.luxury_sofa)
            )
            "Baskets" -> listOf(
                displayProduct("Woven Basket", R.drawable.woven_basket),
                displayProduct("Decorative Basket", R.drawable.decorative_basket)
            )
            "Tables" -> listOf(
                displayProduct("Coffee Table", R.drawable.coffee_table),
                displayProduct("Dining Table", R.drawable.dining_table)
            )
            "Plant Stands" -> listOf(
                displayProduct("Indoor Plant Stand", R.drawable.in_plantstand)
            )
            else -> listOf(
                displayProduct("Chair", R.drawable.modern_chair),
                displayProduct("Basket", R.drawable.woven_basket),
                displayProduct("Table Design", R.drawable.sample_post),
                displayProduct("Sofa", R.drawable.comfort_sofa),
                displayProduct("Table", R.drawable.coffee_table),
                displayProduct("Plant Stands", R.drawable.in_plantstand)
            )
        }
    }

    private fun updateProductList(category: String) {
        val filteredProducts = getDisplayProducts(category)
        displayProductAdapter.updateProductList(filteredProducts)
    }
}
