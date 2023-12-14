package com.example.movierating.presentation.movieScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.movierating.R
import com.example.movierating.data.sealed.NavigationDrawerMenuItem

@Composable
fun NavigationDrawerContent(itemSelected: (navigationDrawerMenuItem: NavigationDrawerMenuItem) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            NavigationDrawerHeader()
            NavigationDrawerMenuItems(itemSelected)
        }
    }
}

@Composable
private fun NavigationDrawerHeader() {
    Box(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }
    Text(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
        text = "Username"
    )
    Divider(modifier = Modifier.padding(top = 16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerMenuItems(itemSelected: (navigationDrawerMenuItem: NavigationDrawerMenuItem) -> Unit) {
    val items = listOf(
        NavigationDrawerMenuItem.Favorite,
        NavigationDrawerMenuItem.Watchlist,
        NavigationDrawerMenuItem.Exit
    )
    val selectedItem = remember { mutableStateOf<NavigationDrawerMenuItem?>(null) }
    for (i in items) {
        NavigationDrawerItem(
            label = {
                Text(
                    ContextCompat.getString(
                        LocalContext.current,
                        i.titleResId
                    )
                )
            },
            selected = selectedItem.value == i,
            onClick = {
                selectedItem.value = i
                itemSelected(i)
            })
    }
}
