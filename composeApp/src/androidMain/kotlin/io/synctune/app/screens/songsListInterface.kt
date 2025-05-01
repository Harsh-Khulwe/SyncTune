package io.synctune.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MusicList(navigateToLocalPlayer: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        TopSearchBar()

        CategoryTabs()

        DropdownSelector()

        SongList(navigateToLocalPlayer)

        MiniPlayer()
    }
}

@Composable
fun TopSearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Tune, contentDescription = "Filter", tint = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
    }
}

@Composable
fun CategoryTabs() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CategoryItem(Icons.Default.AccessTime, "Recent")
        CategoryItem(Icons.Default.FavoriteBorder, "Favorites")
        CategoryItem(Icons.Default.LibraryMusic, "Play list")
    }
}

@Composable
fun CategoryItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = text, tint = Color(0xFFBAA4D6))
        Text(text, color = Color(0xFFBAA4D6), fontSize = 12.sp)
    }
}

@Composable
fun DropdownSelector() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Lorem ipsum",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.GridView, contentDescription = "Grid View", tint = Color.Gray)
    }
}

@Composable
fun SongList(navigateToLocalController: () -> Unit) {
    LazyColumn {
        items(10) { index ->
            SongItem(navigateToController = navigateToLocalController)
        }
    }
}

@Composable
fun SongItem(navigateToController: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { navigateToController() }
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFD5C2E3), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.White)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("Song title", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Lorem ipsum / Lorem ipsum", fontSize = 12.sp, color = Color.Gray)
        }

        Icon(Icons.Default.MoreVert, contentDescription = "More Options", tint = Color.Gray)
    }
}

@Composable
fun MiniPlayer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFD5C2E3), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.White)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("Song title", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Lorem ipsum", fontSize = 12.sp, color = Color.Gray)
        }

        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black)
        Spacer(modifier = Modifier.width(12.dp))
        Icon(Icons.AutoMirrored.Filled.QueueMusic, contentDescription = "Playlist", tint = Color.Black)
    }
}

//@Composable
//@Preview
//fun ListPreview() {
//    MusicList()
//}
