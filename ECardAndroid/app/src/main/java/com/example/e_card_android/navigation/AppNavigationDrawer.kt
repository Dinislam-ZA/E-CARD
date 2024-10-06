package com.example.e_card_android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.e_card_android.R

@Composable
fun AppNavigationDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    Content: @Composable() ()-> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        NavigationItem(
            title = stringResource(R.string.games),
            icon = Icons.Rounded.Home,
            route = Routes.HOME.value
        ),
        NavigationItem(
            title = stringResource(R.string.players_statistic),
            icon = Icons.Rounded.AccountCircle,
            route = Routes.PLAYERS.value
        ),
        NavigationItem(
            title = stringResource(R.string.friends),
            icon = Icons.Rounded.Face,
            route = Routes.FRIENDS.value
        )
    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("E-Card game", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        icon = {item.icon},
                        selected = (currentDestination?.hierarchy?.any { it.route == item.route } == true),
                        onClick = { navController.navigate(item.route) }
                    )
                }
            }
        }
    ) {
        Content()
    }
}