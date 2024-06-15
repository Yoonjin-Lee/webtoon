package yj.myapplication.view.ask

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import yj.myapplication.data.Message
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AskViewModel @Inject constructor() : ViewModel() {
    private val _chatList = MutableLiveData<ArrayList<Message>>()
    val chatList: MutableLiveData<ArrayList<Message>> get() = _chatList

    fun finish(
        context: Context
    ) {
        (context as Activity).finish()
    }

    fun sendMessage(
        text: String
    ) {
        val listOfMessage = ArrayList<Message>()
        listOfMessage.addAll(_chatList.value.orEmpty())
        listOfMessage.add(
            Message(
                message = text,
                time = (if(LocalTime.now().hour < 10) "0${LocalTime.now().hour}" else LocalTime.now().hour.toString())
                    + ":" + (if(LocalTime.now().minute < 10) "0${LocalTime.now().minute}" else LocalTime.now().minute.toString()),
                isMe = true
            )
        )
        _chatList.value = listOfMessage
        Log.d("chatList", _chatList.value.toString())
    }

    fun getMessage(){

    }
}