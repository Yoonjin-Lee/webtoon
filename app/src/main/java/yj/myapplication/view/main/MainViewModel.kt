package yj.myapplication.view.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.myapplication.data.Work
import yj.myapplication.view.add.AddActivity
import yj.myapplication.view.ask.AskActivity
import yj.myapplication.view.login.LoginActivity
import yj.myapplication.view.viewer.ViewerActivity
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _dayList = MutableLiveData<ArrayList<ArrayList<HashMap<String, Any>>>>()
    val dayList: LiveData<ArrayList<ArrayList<HashMap<String, Any>>>> get() = _dayList

    // 웹툰 추가 이동
    fun moveToAdd() {
        val intent = Intent(context, AddActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    // 웹툰 이동
    fun moveToWeb(work: Work){
        val urlMap = mapOf(
            "네이버" to "https://m.comic.naver.com/search/result?keyword=${work.title}",
            "카카오페이지" to "https://page.kakao.com/search/result?keyword=${work.title}",
            "리디" to "https://ridibooks.com/search?q=${work.title}&adult_exclude=n",
            "레진코믹스" to "https://www.lezhin.com/ko/search?t=all&q=${work.title}",
            "봄툰" to "https://www.bomtoon.com/search?q=${work.title}",
            "기타" to work.url
        )

        val intent = Intent(context, ViewerActivity::class.java)
        intent.putExtra("url", urlMap[work.platform])
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    // gemini 채팅
    fun moveToAsk(){
        val intent = Intent(context, AskActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    fun logout(){
        Firebase.auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context, intent, null)
    }

    // 시작할 때, 저장된 웹툰 리스트를 가져옴
    fun getWebtoonList() {
        val db = Firebase.firestore
        val auth = Firebase.auth
        val docRef = db.collection("week").document(auth.uid.toString())
        docRef.get().addOnSuccessListener {
            val data = it.data
            if (data != null) { //데이터가 있는 경우
                getDayList(data)
            } else {
                //데이터가 없는 경우
                Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("getWeekList", "error : $it")
        }
    }

    // 날짜 별 데이터를 정렬
    private fun getDayList(data: Map<String, Any>) {
        val days = arrayListOf(
            "월", "화", "수", "목", "금", "토", "일"
        )

        val saveList = ArrayList<ArrayList<HashMap<String, Any>>>()

        for (day: String in days) {
            if (data.containsKey(day)) {
                val dayData = data[day] as ArrayList<HashMap<String, Any>>
                Log.d("dayData", "$dayData")
                saveList.add(dayData)
            } else {
                saveList.add(arrayListOf())
            }
        }

        _dayList.value = saveList

    }

    init {
        getWebtoonList()
    }
}