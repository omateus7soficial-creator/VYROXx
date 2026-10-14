package com.vyrox.editor

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VyroxEditor()
        }
    }
}

@Composable
fun VyroxEditor() {
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        videoUri = uri
    }

    val bg = Color(0xFF101014)
    val purple = Color(0xFF8B5CF6)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = bg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "VYROX",
                    color = purple,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "VIDEO EDITOR",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(
                            Color(0xFF202027),
                            RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (videoUri != null) {
                        key(videoUri) {
                            AndroidView(
                                modifier = Modifier.fillMaxSize(),
                                factory = { context ->
                                    VideoView(context).apply {
                                        setVideoURI(videoUri)
                                        setOnPreparedListener { player ->
                                            player.setOnVideoSizeChangedListener {
                                                    _, _, _ ->
                                                }
                                            player.isLooping = false
                                            start()
                                        }
                                    }
                                },
                                update = { view ->
                                    if (view.tag != videoUri) {
                                        view.tag = videoUri
                                        view.setVideoURI(videoUri)
                                        view.setOnPreparedListener {
                                            it.start()
                                        }
                                    }
                                }
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "▶",
                                color = purple,
                                fontSize = 48.sp
                            )
                            Text(
                                text = "Seu vídeo aparecerá aqui",
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        videoPicker.launch(arrayOf("video/*"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purple
                    )
                ) {
                    Text("＋  Escolher vídeo")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "FERRAMENTAS DE EDIÇÃO",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ToolCard("✂", "Cortar", Modifier.weight(1f))
                    ToolCard("♫", "Música", Modifier.weight(1f))
                    ToolCard("T", "Texto", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ToolCard("✨", "Efeitos", Modifier.weight(1f))
                    ToolCard("🎨", "Filtros", Modifier.weight(1f))
                    ToolCard("HD", "Qualidade", Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Create Without Limits",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ToolCard(
    symbol: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(82.dp)
            .background(
                Color(0xFF202027),
                RoundedCornerShape(14.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(symbol, color = Color(0xFFB49AFF), fontSize = 23.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(label, color = Color.White, fontSize = 11.sp)
    }
}
