package com.craftersparadise.artisans

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ArtisanSellProductActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var widthEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var weightCapacityEditText: EditText
    private lateinit var materialEditText: EditText
    private lateinit var careMaintenanceEditText: EditText
    private lateinit var addImageButton: Button
    private lateinit var saveButton: Button

    private lateinit var shopTitleView: TextView
    private lateinit var shopLocationView: TextView
    private lateinit var selectedImageView: ImageView // New ImageView for the selected image

    private lateinit var shopName: String
    private lateinit var shopLocation: String

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_sell_product)

        // Initialize the EditText fields, buttons, and ImageViews
        nameEditText = findViewById(R.id.send_productname)
        priceEditText = findViewById(R.id.send_productprice)
        descriptionEditText = findViewById(R.id.send_productdesc)
        widthEditText = findViewById(R.id.send_productwidth)
        heightEditText = findViewById(R.id.send_productheight)
        weightCapacityEditText = findViewById(R.id.send_productweightcapacity)
        materialEditText = findViewById(R.id.send_productmaterial)
        careMaintenanceEditText = findViewById(R.id.send_productcare)
        addImageButton = findViewById(R.id.sendbtn_addimage)
        saveButton = findViewById(R.id.sendbtn_save)

        // Initialize the TextViews for shop name and location
        shopTitleView = findViewById(R.id.sell_shopName)
        shopLocationView = findViewById(R.id.sell_shopLocation)

        // Initialize the selected ImageView
        selectedImageView = findViewById(R.id.sell_selectedImage)

        // Retrieve shop details from Intent
        shopName = intent.getStringExtra("shopName") ?: "Unknown Shop"
        shopLocation = intent.getStringExtra("shopLocation") ?: "Unknown Location"

        // Set the shop details in the TextViews
        shopTitleView.text = shopName
        shopLocationView.text = shopLocation

        // Set a click listener for the add image button
        addImageButton.setOnClickListener {
            openImageChooser()
        }

        saveButton.setOnClickListener {
            if (areFieldsFilled()) {
                val productName = nameEditText.text.toString()
                Toast.makeText(this, "Product '$productName' saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to check if all EditTexts are filled
    private fun areFieldsFilled(): Boolean {
        return nameEditText.text.isNotEmpty() &&
                priceEditText.text.isNotEmpty() &&
                descriptionEditText.text.isNotEmpty() &&
                widthEditText.text.isNotEmpty() &&
                heightEditText.text.isNotEmpty() &&
                weightCapacityEditText.text.isNotEmpty() &&
                materialEditText.text.isNotEmpty() &&
                careMaintenanceEditText.text.isNotEmpty()
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            selectedImageView.setImageURI(imageUri)
            selectedImageView.visibility = ImageView.VISIBLE
        }
    }
}
