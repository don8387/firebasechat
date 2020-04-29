package fire.base.chat.presentation.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fire.base.chat.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessengerFragment : Fragment() {

    private val messengerViewModel: MessengerViewModel by viewModel()

    private lateinit var inputBox: EditText
    private lateinit var messagesList: RecyclerView
    private lateinit var sendMessageButton: ImageButton

    private val messageAdapter =
        MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.messenger_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        subscribeOnDataSources()
//        addBots(1)
    }

    private fun addBots(botCount: Int) {
        messengerViewModel.addBots(botCount)
    }

    private fun initViews(view: View) {
        inputBox = view.findViewById(R.id.input_box)

        messagesList = view.findViewById(R.id.message_recycler_view)
        messagesList.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                true
            )
            adapter = messageAdapter
            addItemDecoration(MessageItemDecorator())
        }.smoothScrollToPosition(0)

        sendMessageButton = view.findViewById(R.id.send_message)
        sendMessageButton.setOnClickListener {
            inputBox.text
                ?.toString()
                ?.takeIf { !it.isBlank() }
                ?.let { typedMessage: String ->
                    messengerViewModel.sendMessage(message = typedMessage)
                    inputBox.text.clear()
                }
        }
    }

    private fun subscribeOnDataSources() {
        messengerViewModel
            .getLastReceivedMessage()
            .observe(
                requireActivity(),
                Observer(::onLastReceivedMessage)
            )
    }

    private fun onLastReceivedMessage(messageItem: MessageItem?) {
        messageItem?.let {
            messageAdapter.addMessage(listOf(it))
        }
        messagesList.smoothScrollToPosition(0)
    }
}
