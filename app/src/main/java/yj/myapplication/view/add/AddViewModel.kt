package yj.myapplication.view.add

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.myapplication.data.Work
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
) : ViewModel() {
    fun finish(context: Context) {
        (context as AddActivity).finish()
    }

    fun write(
        title: String,
        platform: String,
        day: String,
        url: String?,
        context: Context
    ) {
        //firestore에 저장
        val db = Firebase.firestore
        val auth = Firebase.auth
        val docRef = db.collection("week").document(auth.uid.toString())

        docRef.get().addOnSuccessListener {
            if (it != null && it.exists()) { // 문서가 있는 경우
                val data = it.data
                if (data != null && data.containsKey(day)) { // 해당 요일에 대한 필드가 있는지
                    val field = data[day] as ArrayList<Work>
                    field.add(
                        Work(
                            title,
                            platform,
                            day,
                            url
                        )
                    )
                    docRef.update(day, field).addOnSuccessListener {
                        Log.d(TAG, "Success -> $it")
                    }.addOnFailureListener {
                        Log.d(TAG, "Error : Failure -> $it")
                    }
                } else { // 요일 필드 생성 후 업데이트
                    val field = ArrayList<Work>()
                    field.add(
                        Work(
                            title,
                            platform,
                            day,
                            url
                        )
                    )
                    docRef.update(day, field).addOnSuccessListener {
                        Log.d(TAG, "Success -> $it")
                    }.addOnFailureListener {
                        Log.d(TAG, "Error : Failure -> $it")
                    }
                }
            } else { // 문서가 없는 경우
                Log.d(TAG, "No such document")
                val field = ArrayList<Work>()
                field.add(
                    Work(
                        title,
                        platform,
                        day,
                        url
                    )
                )
                val week = hashMapOf(
                    "uid" to auth.uid.toString(),
                    day to field
                )
                docRef.set(week).addOnSuccessListener {
                    Log.d(TAG, "Success -> $it")
                }.addOnFailureListener {
                    Log.d(TAG, "Error : Failure -> $it")
                }
            }
            Toast.makeText(context, "저장 완료", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Log.d(TAG, "Error : Failure -> $it")
        }
    }
}