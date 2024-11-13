package com.craftersparadise.artisans

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.craftersparadise.artisans.databinding.ActivityViewMessageBinding

class ViewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMessageBinding
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recipientName = intent.getStringExtra("recipient_name") ?: "Unknown"
        supportActionBar?.title = recipientName

        chatAdapter = ChatAdapter(chatMessages)
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        binding.rvChat.adapter = chatAdapter

        binding.btnSendMessage.setOnClickListener {
            val message = binding.etCreateMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                chatMessages.add(ChatMessage(message, true)) // true for sent message
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                binding.rvChat.scrollToPosition(chatMessages.size - 1)
                binding.etCreateMessage.text.clear()
            }
        }

        binding.btnAttachmentIcon.setOnClickListener {
            val intent = Intent(this, CustomizeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
