package yj.myapplication.view.ask

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yj.myapplication.data.Message
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AskViewModel @Inject constructor() : ViewModel() {
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
                time = (if(LocalTime.now().hour < 10) "0${LocalTime.now().hour}" else LocalTime.now().hour.toString())
                    + ":" + (if(LocalTime.now().minute < 10) "0${LocalTime.now().minute}" else LocalTime.now().minute.toString()),
                isMe = true
            )
        )
        _chatList.value = ArrayList(listOfMessage.reversed())
        Log.d("chatList", _chatList.value.toString())

        askGemini(text)
    }

    private fun getMessage(text: String){
        listOfMessage.add(
            Message(
                message = text,
                time = (if(LocalTime.now().hour < 10) "0${LocalTime.now().hour}" else LocalTime.now().hour.toString())
                        + ":" + (if(LocalTime.now().minute < 10) "0${LocalTime.now().minute}" else LocalTime.now().minute.toString()),
                isMe = false
            )
        )
        _chatList.value = ArrayList(listOfMessage.reversed())
        Log.d("chatList", _chatList.value.toString())
    }

    private fun askGemini(
        text: String
    ){
        var response = String()
        viewModelScope.launch(Dispatchers.IO){
            geminiModel.generateContentStream(text).collect{
                response += it
            }
            Log.d("response", response)
        }
        if (response.isNotBlank()) {
            getMessage(response)
        }else{
            getMessage("오류")
        }
    }

    init {
//        askGemini("안녕")
    }
}