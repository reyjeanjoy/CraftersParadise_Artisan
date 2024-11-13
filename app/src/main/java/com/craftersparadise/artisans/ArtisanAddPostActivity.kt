package com.craftersparadise.artisans

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ArtisanAddPostActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imagePreview: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var postButton: Button
    private lateinit var editText: EditText
    private var imageUri: Uri? = null  // Store the selected image URI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_add_post)

        imagePreview = findViewById(R.id.addPostImagePreview)
        selectImageButton = findViewById(R.id.addPostSelectImageButton)
        postButton = findViewById(R.id.addPostButton)
        editText = findViewById(R.id.addPostEditText)

        // Set up the button to select an image
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Set up the post button click listener
        postButton.setOnClickListener {
            val postText = editText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("postText", postText)
                putExtra("mediaUri", imageUri?.toString()) // Pass the URI as String
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            data?.data?.let { uri ->
                imageUri = uri
                // Display the selected image in the ImageView
                imagePreview.setImageURI(uri)
                imagePreview.visibility = View.VISIBLE
            }
        }
    }
}
