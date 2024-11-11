package com.example.e_card_android.ui.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.e_card_android.R
import com.example.e_card_android.data.model.Notification

@Composable
fun FriendInviteNotificationItem(
    notification: Notification.FriendInviteNotification
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = notification.time.toString()
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = notification.title,
                fontSize = 24.sp,
            )
            Text(
                text = notification.sender.username,
                fontSize = 18.sp,
            )
        }
        Image(
            painter = rememberAsyncImagePainter(notification.sender.profilePictureUrl),
            contentDescription = stringResource(
                R.string.profile_picture_of,
                notification.sender.username
            ),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun GameInviteNotificationItem(
    notification: Notification.GameInviteNotification
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = notification.time.toString()
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = notification.title,
                fontSize = 24.sp,
            )
            Row {
                Text(
                    text = notification.game.owner.username,
                    fontSize = 18.sp,
                )
                Text(
                    text = notification.game.bet.toString(),
                    fontSize = 18.sp,
                )
                Text(
                    text = ", ${notification.game.rounds} rounds",
                    fontSize = 18.sp,
                )
            }
        }
        Image(
            painter = rememberAsyncImagePainter(notification.game.owner.profilePictureUrl),
            contentDescription = stringResource(
                R.string.profile_picture_of,
                notification.game.owner.username
            ),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun NewsNotificationItem(
    notification: Notification.NewsNotification
){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = notification.time.toString()
        )
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = notification.title,
                fontSize = 24.sp,
            )
            Text(
                text = notification.description,
                fontSize = 18.sp
            )
        }
    }
}