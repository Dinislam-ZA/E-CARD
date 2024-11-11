package com.example.e_card_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_card_android.data.network.AuthManager
import com.example.e_card_android.data.network.AuthManagerImpl
import com.example.e_card_android.navigation.AppNavigationDrawer
import com.example.e_card_android.navigation.MainNavigation
import com.example.e_card_android.navigation.Routes
import com.example.e_card_android.ui.theme.ECardAndroidTheme
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {

    private val authManager: AuthManager by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECardAndroidTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                AppNavigationDrawer(
                    navController = navController,
                    drawerState = drawerState,
                    onQuitButtonClick = {
                        scope.launch {
                            logoutUser()
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            val navBackStackEntry =
                                navController.currentBackStackEntryAsState().value
                            val currentRoute = navBackStackEntry?.destination?.route
                            TopAppBar(
                                title = { currentRoute?.let { Text(text = it) } },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                                    }
                                },
                                actions = {
                                    if (currentRoute != Routes.NOTIFICATIONS.value)
                                        BadgedBox(
                                            modifier = Modifier
                                                .padding(
                                                    start = 0.dp,
                                                    bottom = 0.dp,
                                                    end = 16.dp,
                                                    top = 0.dp
                                                )
                                                .clickable {
                                                    navController.navigate(Routes.NOTIFICATIONS.value)
                                                },
                                            badge = {
                                                Badge {
                                                    val badgeNumber =
                                                        "8" // TODO: notifications number from notifications service
                                                    Text(
                                                        badgeNumber,
                                                    )
                                                }
                                            }
                                        ) {
                                            Icon(
                                                Icons.Filled.Notifications,
                                                contentDescription = stringResource(R.string.notifications_icon)
                                            )
                                        }
                                }
                            )
                        }
                    ) { innerPadding ->
                        MainNavigation(navController, innerPadding)
                    }
                }
            }
        }
    }

    private suspend fun logoutUser() {
        val result = authManager.logout()
        if (result == HttpStatusCode.OK) {
            goToAuthActivity()
        }
    }

    private fun goToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToGameActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
