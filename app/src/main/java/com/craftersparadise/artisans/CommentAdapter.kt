package com.craftersparadise.artisans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Comment(
    val userName: String,
    val commentText: String
)

class CommentsAdapter(private var comments: MutableList<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    fun updateComments(newComments: List<Comment>) {
        comments = newComments.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val commentTextView: TextView = view.findViewById(R.id.commentTextView)
        private val userNameTextView: TextView = view.findViewById(R.id.userNameTextView)

        fun bind(comment: Comment) {
            userNameTextView.text = comment.userName
            commentTextView.text = comment.commentText
        }
    }
}
