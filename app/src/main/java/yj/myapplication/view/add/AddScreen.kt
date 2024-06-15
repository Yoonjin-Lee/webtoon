package yj.myapplication.view.add

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yj.myapplication.R
import yj.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    context: Context
) {
    val viewModel = hiltViewModel<AddViewModel>()

    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    // 플랫폼 라디오 그룹
    val radioGroup = remember {
        mutableStateListOf(
            "네이버",
            "카카오페이지",
            "리디",
            "레진코믹스",
            "봄툰",
            "기타"
        )
    }

    val radioImageGroup = remember {
        mutableStateListOf(
            R.drawable.naver_logo,
            R.drawable.kakaopage_logo,
            R.drawable.ridi_logo,
            R.drawable.lezhin_logo,
            R.drawable.bomtoon_logo,
            R.drawable.ic_launcher_background
        )
    }

    val selectedIndex = remember { mutableStateOf(radioGroup[0]) }

    // 요일 라디오 그룹
    val radioDayGroup = arrayListOf(
        "월",
        "화",
        "수",
        "목",
        "금",
        "토",
        "일"
    )

    val selectedDay = remember {
        mutableStateOf(radioDayGroup[0])
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("웹툰 추가") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.finish(context)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(
                    color = colorResource(id = R.color.white),
                )
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        if (selectedIndex.value == "기타") {
                            if (url.isNotBlank()){
                                // 서버에 저장
                                viewModel.write(
                                    title = title,
                                    url = url,
                                    day = selectedDay.value,
                                    platform = selectedIndex.value,
                                    context = context
                                )
                            }else{
                                Toast.makeText(context, "URL을 입력해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            // 서버에 저장
                            viewModel.write(
                                title = title,
                                url = url,
                                day = selectedDay.value,
                                platform = selectedIndex.value,
                                context = context
                            )
                        }
                    }
                ) {
                    Text(text = "추가")
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var isVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = title,
                onValueChange = {s: String ->  title = s },
                label = { Text("제목") },
                singleLine = true
            )


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp, 10.dp),
            ) {
                this.items(radioGroup.size) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = radioGroup[it])
                        Image(
                            painter = painterResource(id = radioImageGroup[it]),
                            contentDescription = "logo",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.size(50.dp)
                        )
                        RadioButton(
                            selected = selectedIndex.value == radioGroup[it],
                            onClick = {
                                selectedIndex.value = radioGroup[it]
                                isVisible = when(selectedIndex.value){
                                    "기타" -> true
                                    else -> false
                                }
                            }
                        )
                    }
                }
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                radioDayGroup.forEachIndexed {_, s ->
                    OutlinedButton(
                        onClick = { selectedDay.value = s },
                        modifier = Modifier.width(50.dp),
                        colors = if (selectedDay.value == s) {
                            ButtonDefaults.outlinedButtonColors(
                                contentColor = colorResource(id = R.color.white),
                                containerColor = colorResource(id = R.color.black)
                            )
                        } else {
                            ButtonDefaults.outlinedButtonColors(
                                contentColor = colorResource(id = R.color.black),
                                containerColor = colorResource(id = R.color.white)
                            )
                        }
                    ) {
                        Text(
                            text = s,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (isVisible) {
                OutlinedTextField(
                    modifier = Modifier.padding(10.dp),
                    value = url,
                    onValueChange = {s: String ->  url = s},
                    label = { Text("플랫폼 URL") },
                    singleLine = true
                )
            }
        }
    }
}