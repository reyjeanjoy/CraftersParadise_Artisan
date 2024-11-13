package com.craftersparadise.artisans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Post(
    val shopName: String,
    val postText: String,
    val mediaUri: String?,
    var isLiked: Boolean = false,
    var likeCount: Int = 0,
    var comments: MutableList<Comment> = mutableListOf(),
    var commentCount: Int = 0 // New property to count comments
)


class PostsAdapter(private var posts: MutableList<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    fun updatePosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    // Add new post at the top of the list
    fun addPost(post: Post) {
        posts.add(0, post)  // Add the post at the beginning of the list
        notifyItemInserted(0)  // Notify that a new item is inserted at position 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.artisan_post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shopNameTextView: TextView = view.findViewById(R.id.postItemShopName)
        val postTextView: TextView = view.findViewById(R.id.postItemText)
        val postImageView: ImageView = view.findViewById(R.id.postItemImage)
        val likeButton: TextView = view.findViewById(R.id.likeButton)
        val commentButton: TextView = view.findViewById(R.id.commentButton)
        val commentsSection: View = view.findViewById(R.id.commentsSection)
        val commentsRecyclerView: RecyclerView = view.findViewById(R.id.commentsRecyclerView)
        val addCommentEditText: EditText = view.findViewById(R.id.addCommentEditText)
        val addCommentButton: ImageButton = view.findViewById(R.id.addCommentIcon)

        fun bind(post: Post) {
            shopNameTextView.text = post.shopName
            postTextView.text = post.postText

            // Handle media URI
            if (post.mediaUri != null) {
                Glide.with(postImageView.context)
                    .load(post.mediaUri)
                    .into(postImageView)
                postImageView.visibility = View.VISIBLE
            } else {
                postImageView.visibility = View.GONE
            }

            // Update like button text
            likeButton.text =
                if (post.isLiked) "Liked (${post.likeCount})" else "Like (${post.likeCount})"

            // Update comment button text
            commentButton.text = "Comment (${post.commentCount})" // Display comment count

            // Handle like functionality
            likeButton.setOnClickListener {
                post.isLiked = !post.isLiked
                post.likeCount += if (post.isLiked) 1 else -1
                notifyItemChanged(adapterPosition)
            }

            // Handle comment button click (show comments section)
            commentButton.setOnClickListener {
                commentsSection.visibility =
                    if (commentsSection.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            // Set up the comments RecyclerView with the adapter
            val commentsAdapter = CommentsAdapter(post.comments)
            commentsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            commentsRecyclerView.adapter = commentsAdapter

            // Handle adding a new comment with the icon
            addCommentButton.setOnClickListener {
                val newCommentText = addCommentEditText.text.toString().trim()
                if (newCommentText.isNotEmpty()) {
                    val newComment =
                        Comment(userName = "CraftersParadise", commentText = newCommentText)
                    post.comments.add(newComment)
                    post.commentCount++
                    commentButton.text =
                        "Comment (${post.commentCount})"
                    addCommentEditText.text.clear()
                    commentsAdapter.updateComments(post.comments)
                }
            }
        }
    }
}