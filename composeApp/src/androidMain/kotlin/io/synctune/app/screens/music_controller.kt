package io.synctune.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import io.synctune.app.R

@Composable
fun MusicController() {

    Column(
        modifier = Modifier
            .background(Color(239, 241, 245))
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Songs list dropdown",
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    "Song Name",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painterResource(id = R.drawable.music_logo),
            contentDescription = "Music Logo",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Song Title",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        Text(text = "Singer Name")

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "07:15", fontSize = 16.sp, color = Color.Gray)
            Text(text = "07:46", fontSize = 16.sp, color = Color.Gray)
        }

        Slider(
            value = 0.7f, // Example value, you can bind this to a state
            onValueChange = { /* Handle value change */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Handle previous button click */ }) {
                Text(text = "⏮", fontSize = 24.sp)
            }
            IconButton(onClick = { /* Handle play/pause button click */ }) {
                Text(text = "⏯", fontSize = 24.sp)
            }
            IconButton(onClick = { /* Handle next button click */ }) {
                Text(text = "⏭", fontSize = 24.sp)
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = false)
//@Composable
//fun PreviewMusicController() {
//    MusicController()
//}