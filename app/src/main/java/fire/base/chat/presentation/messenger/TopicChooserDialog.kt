package fire.base.chat.presentation.messenger

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.core.content.edit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fire.base.chat.FireBaseChatConstants.TOPIC_SHARED_PREF_KEY
import fire.base.chat.R

class TopicChooserDialog : BottomSheetDialogFragment() {

    private lateinit var topicInput: EditText
    private lateinit var connectButton: View

    private var callback: TopicChooserDialogCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TopicDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.topic_chooser_dialog,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        initViews(view)
    }

    private fun initViews(view: View) {
        with(view) {
            topicInput = findViewById(R.id.topic_input)

            connectButton = findViewById(R.id.confirm_topic)
            connectButton.setOnClickListener {
                saveTopic()
            }
        }
    }

    private fun saveTopic() {
        topicInput
            .text
            ?.toString()
            ?.takeIf { !it.isBlank() }
            ?.let { topic: String ->
                requireActivity()
                    .getPreferences(MODE_PRIVATE)
                    .edit {
                        putString(TOPIC_SHARED_PREF_KEY, topic)
                    }.also {
                        callback?.onTopicSaved()
                        dismiss()
                    }
            }
    }

    override fun onStart() {
        super.onStart()

        topicInput.requestFocus()
    }

    public fun setCallback(callback: TopicChooserDialogCallback) {
        this.callback = callback
    }
}


