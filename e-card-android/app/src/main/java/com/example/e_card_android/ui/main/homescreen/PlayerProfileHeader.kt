package com.example.e_card_android.ui.main.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.theme.DarkGreen

@Composable
fun PlayerProfileHeader(user: User) {
    Row (
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(user.profilePictureUrl),
            contentDescription = "Profile Picture of ${user.username}",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier.width(16.dp)
        )
        Column(modifier = Modifier
            .weight(1f)
            .padding(end = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.username,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${user.money} $",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = DarkGreen
            )
        }
    }
}

@Preview
@Composable
fun PlayerProfileHeaderPreview(){
    PlayerProfileHeader(user = User(0, "Oldman", money = 100000))
}