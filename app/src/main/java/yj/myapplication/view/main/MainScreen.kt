package yj.myapplication.view.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import yj.myapplication.R
import yj.myapplication.data.Work
import yj.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
) {
    val viewModel = hiltViewModel<MainViewModel>()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val auth = Firebase.auth

    //viewModel에서 데이터 받아오기
    val dayList by viewModel.dayList.observeAsState(initial = emptyList())

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerContentColor = Color.Black
            ) {
                Text(
                    text = auth.currentUser?.displayName.toString(),
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Gemini 추천 작품") },
                    selected = false,
                    onClick = { /*TODO*/ },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White
                    ))
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "로그아웃",
                            style = TextStyle(
                                color = Color.Red
                            )
                        )
                    },
                    selected = false,
                    onClick = { viewModel.logout() },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.White
                    ))
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("WebToon") },
                    navigationIcon = { // 사용자 계정에 관한 정보를 담을 예정
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "top"
                            )
                        }
                    },
                    actions = { // 더하는 버튼
                        IconButton(onClick = {
                            viewModel.moveToAdd()
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "add"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "월",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[0]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "화",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[1]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "수",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[2]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "목",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[3]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "금",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[4]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "토",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[5]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1 / 7f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "일",
                            maxLines = 2,
                            overflow = TextOverflow.Clip
                        )
                        LazyColumn {
                            item {
                                if (dayList.isNotEmpty()) {
                                    for (i in dayList[6]) {
                                        val work = Work(
                                            title = i["title"].toString(),
                                            platform = i["platform"].toString(),
                                            url = i["url"].toString(),
                                            day = i["day"].toString()
                                        )
                                        IconLayout(Modifier.weight(1.0f, true), work)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IconLayout(
    modifier: Modifier,
    work: Work
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val imageMap = mapOf(
        "네이버" to R.drawable.naver_logo,
        "카카오페이지" to R.drawable.kakaopage_logo,
        "리디" to R.drawable.ridi_logo,
        "레진코믹스" to R.drawable.lezhin_logo,
        "봄툰" to R.drawable.bomtoon_logo,
        "기타" to R.drawable.ic_launcher_background
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                viewModel.moveToWeb(work)
            }) {
            Image(
                painter = painterResource(
                    id = imageMap[work.platform] ?: R.drawable.ic_launcher_background
                ),
                contentDescription = "플랫폼",
                contentScale = ContentScale.Inside
            )
        }
        Text(
            text = work.title,
            style = TextStyle(
                fontSize = 13.sp
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview(
    context: Context = LocalContext.current
) {
    MyApplicationTheme {
        MainScreen()
    }
}