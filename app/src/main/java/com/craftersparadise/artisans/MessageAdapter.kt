import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.craftersparadise.artisans.databinding.ListItemMessageBinding

data class Message(
    val name: String,
    val message: String,
    val time: String,
    val profileImageResId: Int // Changed from Int to String
)

class MessageAdapter(
    private val messages: List<Message>,
    private val onClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(val binding: ListItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessName.text = message.name
            binding.tvMessage.text = message.message
            binding.tvMessageTime.text = message.time

            // Load image using a string resource name
            val resourceId = binding.root.context.resources.getIdentifier(message.profileImageResId.toString(), "drawable", binding.root.context.packageName)
            Glide.with(binding.root.context)
                .load(resourceId)
                .into(binding.ivMessageProfileImage)

            binding.root.setOnClickListener { onClick(message) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ListItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}
