package fire.base.chat.presentation.messenger

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fire.base.chat.FireBaseChatConstants.IS_FIRST_START
import fire.base.chat.FireBaseChatConstants.TOPIC_SHARED_PREF_KEY
import fire.base.chat.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessengerFragment : Fragment(), TopicChooserDialogCallback {

    private val messengerViewModel: MessengerViewModel by viewModel()

    private lateinit var inputBox: EditText
    private lateinit var messagesList: RecyclerView
    private lateinit var sendMessageButton: ImageButton
    private lateinit var connectionStateIcon: View
    private lateinit var connectionStateText: TextView

    private val messageAdapter = MessageAdapter()

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
    }

    override fun onStart() {
        super.onStart()

        isTopicChooserNeeded()
            .takeIf { it }
            .run { showTopicChooserDialog() }
    }

    private fun showTopicChooserDialog() {
        TopicChooserDialog()
            .apply { setCallback(this@MessengerFragment) }
            .show(requireActivity().supportFragmentManager, null)
    }

    private fun isTopicChooserNeeded(): Boolean = requireActivity()
        .getPreferences(MODE_PRIVATE)
        .getBoolean(IS_FIRST_START, true)

    override fun onStop() {
        super.onStop()

        disconnect()
    }

    private fun disconnect() {
        messengerViewModel.unsubscribeFromTopic()
    }

    private fun addBots(botCount: Int) {
        messengerViewModel.addBots(botCount)
    }

    private fun initViews(view: View) {
        inputBox = view.findViewById(R.id.input_box)

        connectionStateIcon = view.findViewById(R.id.is_topic_connected)
        connectionStateText = view.findViewById(R.id.topic_connection_desc)

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
                Observer(::updateMessageList)
            )

        messengerViewModel
            .isFirebaseConnected()
            .observe(
                requireActivity(),
                Observer {
                    changeConnectionStateView(it)
                    addBots(3)
                }
            )
    }

    private fun changeConnectionStateView(isConnected: Boolean?) {
        isConnected?.let { connected: Boolean ->
            if (connected) ConnectionStateDesc.CONNECTED else ConnectionStateDesc.DISCONNECTED
        }?.let { stateDesc: ConnectionStateDesc ->
            connectionStateIcon.background = resources.getDrawable(stateDesc.drawableId)
            connectionStateText.text = getString(stateDesc.stringId)
        }
    }

    private fun updateMessageList(messageItem: MessageItem?) {
        messageItem?.let {
            messageAdapter.addMessage(listOf(it))
        }
        messagesList.smoothScrollToPosition(0)
    }

    override fun onTopicSaved() {
        requireActivity()
            .getPreferences(MODE_PRIVATE)
            .getString(TOPIC_SHARED_PREF_KEY, null)
            ?.let { newTopic: String ->
                messengerViewModel.setActiveTopic(newTopic)
                messengerViewModel.subscribeToTopic()
            }
    }
}

