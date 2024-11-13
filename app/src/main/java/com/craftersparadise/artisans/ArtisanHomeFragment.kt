package com.craftersparadise.artisans


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class ArtisanHomeFragment : Fragment() {

    private lateinit var shopProfilesRecyclerView: RecyclerView
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter

    private val addPostResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val postText = result.data?.getStringExtra("postText")
                val mediaUriString = result.data?.getStringExtra("mediaUri")

                if (postText != null || mediaUriString != null) {
                    val newPost = Post(
                        shopName = "CraftersParadise",
                        postText = postText ?: "",
                        mediaUri = mediaUriString
                    )
                    postsAdapter.addPost(newPost)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artisan_home, container, false)

        // Initialize RecyclerViews
        shopProfilesRecyclerView = view.findViewById(R.id.shopProfilesRecyclerView)
        postsRecyclerView = view.findViewById(R.id.postsRecyclerView)

        // Setup Horizontal RecyclerView for Shop Profiles
        val shopProfilesAdapter = ShopProfilesAdapter(requireContext(), getShopProfiles())
        shopProfilesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        shopProfilesRecyclerView.adapter = shopProfilesAdapter

        // Setup Vertical RecyclerView for Posts
        postsAdapter = PostsAdapter(getPosts().toMutableList())
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        postsRecyclerView.adapter = postsAdapter

        // Set up the click listener for the EditText
        val editTextPost: EditText = view.findViewById(R.id.homeWritePostEditText)
        editTextPost.setOnClickListener {
            val intent = Intent(requireContext(), ArtisanAddPostActivity::class.java)
            addPostResultLauncher.launch(intent)
        }

        // Set up the click listener for the search icon
        val searchIcon: ImageView = view.findViewById(R.id.homeSearch_icon)
        searchIcon.setOnClickListener {
            val intent = Intent(requireContext(), ArtisanSearchActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getShopProfiles(): List<ShopProfile> {
        return listOf(
            ShopProfile("Netibo", R.drawable.sample_image1, "Unknown Location"),
            ShopProfile("Christian Rattan", R.drawable.sample_image1, "Unknown Location"),
            ShopProfile("Sandayong Local Weavers", R.drawable.sample_image1, "Unknown Location"),
            ShopProfile("Lolita Rattan", R.drawable.sample_image1, "Unknown Location"),
            ShopProfile("Perez Artisans", R.drawable.sample_image1, "Unknown Location")
        )
    }

    private fun getPosts(): List<Post> {
        return listOf(
            Post(
                "Netibo",
                "Embrace the timeless elegance...",
                "https://media.cnn.com/api/v1/images/stellar/prod/210902160706-rattanleadetsy-daybed-1.jpg?q=x_0,y_0,h_1406,w_2498,c_fill/h_720,w_1280"
            ),
            Post(
                "Christian Rattan",
                "Perfect for adding a touch of natural charm...",
                "https://media.cnn.com/api/v1/images/stellar/prod/210902160706-rattanleadetsy-daybed-1.jpg?q=x_0,y_0,h_1406,w_2498,c_fill/h_720,w_1280"
            ),
            Post(
                "Sandayong",
                "Our handcrafted rattan chair...",
                "https://media.cnn.com/api/v1/images/stellar/prod/210902160706-rattanleadetsy-daybed-1.jpg?q=x_0,y_0,h_1406,w_2498,c_fill/h_720,w_1280"
            )
        )
    }
}
