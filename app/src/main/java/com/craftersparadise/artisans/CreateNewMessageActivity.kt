package com.craftersparadise.artisans

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.craftersparadise.artisans.databinding.ActivityCreateNewMessageBinding

class CreateNewMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendNewMessage.setOnClickListener {
            val recipient = binding.etRecipient.text.toString().trim()
            val messageContent = binding.etCreateNewMessage.text.toString().trim()

            if (recipient.isNotBlank() && messageContent.isNotBlank()) {
                // Simulate sending a message
                sendMessage(recipient, messageContent)
            } else {
                Toast.makeText(this, "Please enter recipient and message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMessage(recipient: String, messageContent: String) {
        // You should handle sending the message here, e.g., saving to a database or server

        Toast.makeText(this, "Message sent to $recipient", Toast.LENGTH_SHORT).show()

        // Finish activity and return result
        val resultIntent = Intent()
        resultIntent.putExtra("recipient", recipient)
        resultIntent.putExtra("message", messageContent)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
