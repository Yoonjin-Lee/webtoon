package yj.myapplication.view.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yj.myapplication.R
import yj.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LoginScreen(
    resultLauncher: ActivityResultLauncher<Intent>
) {
    val viewModel: LoginViewModel = hiltViewModel()

    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.mytoons_logo
                ),
                contentDescription = "MyToons",
                modifier = Modifier
                    .padding(30.dp)
                    .size(500.dp),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.mint),
                    contentColor = colorResource(id = R.color.black),
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = colorResource(id = R.color.black)
                ),
                onClick = {
                    viewModel.googleLogin(resultLauncher)
                }
            ) {
                Text(text = "Google 로그인")
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview(
    context: Context = LocalContext.current
) {
    MyApplicationTheme {
//        LoginScreen(context)
    }
}