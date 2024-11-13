package com.craftersparadise.artisans

import Message
import MessageAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.craftersparadise.artisans.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf(
        Message("Sandayong Local Weavers", "Yes", "New", R.drawable.sample_image1),
        Message("Netibo", "Thank you.", "7:59 PM", R.drawable.sample_image1),
        Message("Perez Artisans", "Okay.", "8:30 AM", R.drawable.sample_image1),
        Message("Lolita Rattan", "Yeah, sure", "1 day ago", R.drawable.sample_image1),
    )
    private val newMessageRequestCode = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        messageAdapter = MessageAdapter(messages) { message ->
            // Pass recipient name to ViewMessageActivity
            val intent = Intent(requireContext(), ViewMessageActivity::class.java)
            intent.putExtra("recipient_name", message.name)
            startActivity(intent)
        }
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = messageAdapter

        // Setup New Message button click listener
        binding.newMessageIcon.setOnClickListener {
            val intent = Intent(requireContext(), CreateNewMessageActivity::class.java)
            startActivityForResult(intent, newMessageRequestCode)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newMessageRequestCode && resultCode == Activity.RESULT_OK) {
            val recipient = data?.getStringExtra("recipient") ?: return
            val messageContent = data.getStringExtra("message") ?: return

            // Add the new message to the top of the list and notify the adapter
            messages.add(0, Message(recipient, messageContent, "Just now", R.drawable.sample_image1))
            messageAdapter.notifyItemInserted(0)
            binding.rvMessages.scrollToPosition(0) // Scroll to the top to show the new message
        }
    }
}
