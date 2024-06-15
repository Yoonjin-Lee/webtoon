package yj.myapplication.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d("resultLauncher", "resultCode : ${result.resultCode}")
                if (result.resultCode == RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        viewModel.firebaseAuthWithGoogle(task.result.idToken!!, FirebaseAuth.getInstance())
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                    // 작업
                }
                // auth 받아오기
                auth = FirebaseAuth.getInstance()
            }


        setContent {
            LoginScreen(
                resultLauncher
            )
        }
    }
}