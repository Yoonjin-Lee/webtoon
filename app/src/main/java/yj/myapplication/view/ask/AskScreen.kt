package yj.myapplication.view.ask

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yj.myapplication.data.Message
import yj.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskScreen(
    context: Context
) {
    val viewModel = hiltViewModel<AskViewModel>()
    val chatInput = remember {
        mutableStateOf("")
    }
    val chatList = viewModel.chatList.observeAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Gemini 채팅") },
                navigationIcon = {
                    // 뒤로 가기 버튼
                    IconButton(onClick = {
                        viewModel.finish(context)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            // 채팅 목록
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = rememberLazyListState(),
                reverseLayout = true
            ) {
                items(chatList.value){message ->
                    ChatBox(message = message)
                }
            }
            // 텍스트 입력 부분
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "입력") },
                    value = chatInput.value,
                    onValueChange = { input: String -> chatInput.value = input },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (chatInput.value.isNotBlank()) {
                                // 메시지 전송
                                viewModel.sendMessage(chatInput.value)
                                chatInput.value = ""
                            }
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "send")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ChatBox(message: Message){
    val modifier = if (message.isMe) {
        Modifier
            .padding(start = 16.dp, end = 8.dp)
            .defaultMinSize(minHeight = 60.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomStart = 20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF007EF4),
                        Color(0xFF2A75BC),
                    )
                )
            )
    } else {
        Modifier
            .padding(start = 8.dp, end = 16.dp)
            .defaultMinSize(minHeight = 60.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomEnd = 20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF454545),
                        Color(0xFF2B2B2B),
                    )
                )
            )
    }

    val boxArrangement = if (message.isMe) Alignment.CenterEnd else Alignment.CenterStart

    Box(modifier = Modifier
        .padding(vertical = 12.dp)
        .fillMaxWidth(), contentAlignment = boxArrangement) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Box(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = message.message,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message.time,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(
) {
    MyApplicationTheme {
        ChatBox(
            Message(
                message = "안녕하세요",
                time = "12:00",
                isMe = false
            )
        )
    }
}