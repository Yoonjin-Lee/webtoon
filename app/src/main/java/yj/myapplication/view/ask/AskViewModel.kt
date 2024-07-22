package yj.myapplication.view.ask

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.noties.markwon.Markwon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yj.myapplication.data.Message
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AskViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    //create gemini model
    private val geminiModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

    private val _chatList = MutableLiveData<ArrayList<Message>>()
    val chatList: MutableLiveData<ArrayList<Message>> get() = _chatList
    private val listOfMessage = ArrayList<Message>()

    fun finish(
        context: Context
    ) {
        (context as Activity).finish()
    }

    fun sendMessage(
        text: String
    ) {
        listOfMessage.add(
            Message(
                message = text,
                time = (if (LocalTime.now().hour < 10) "0${LocalTime.now().hour}" else LocalTime.now().hour.toString())
                        + ":" + (if (LocalTime.now().minute < 10) "0${LocalTime.now().minute}" else LocalTime.now().minute.toString()),
                isMe = true
            )
        )
        _chatList.value = ArrayList(listOfMessage.reversed())
        Log.d("chatList", _chatList.value.toString())

        askGemini(text)
    }

    private fun getMessage(text: String) {
        listOfMessage.add(
            Message(
                message = text,
                time = (if (LocalTime.now().hour < 10) "0${LocalTime.now().hour}" else LocalTime.now().hour.toString())
                        + ":" + (if (LocalTime.now().minute < 10) "0${LocalTime.now().minute}" else LocalTime.now().minute.toString()),
                isMe = false
            )
        )
        _chatList.value = ArrayList(listOfMessage.reversed())
        Log.d("chatList", _chatList.value.toString())
    }

    private fun askGemini(
        text: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getResponse(text)
            withContext(Dispatchers.Main) {
                getMessage(response)
            }
        }
    }

    private suspend fun getResponse(text: String): String {
        val response = geminiModel.generateContent(text).text

        val markwon = Markwon.create(context)

        return markwon.toMarkdown(response.orEmpty()).toString()
    }

    init {
        askGemini("안녕")
    }
}