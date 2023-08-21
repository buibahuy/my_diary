package com.example.mydiary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mydiary.bottombar.BottomBar
import com.example.mydiary.bottombar.BottomBarItem
import com.example.mydiary.bottombar.currentRoute
import com.example.mydiary.nav.GRAPH
import com.example.mydiary.nav.NavigationHost
import com.example.mydiary.ui.theme.MyDiaryTheme
import com.example.mydiary.ui.theme.Primary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyDiary()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyDiary() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val bottomBarList = listOf(
        BottomBarItem.Home,
        BottomBarItem.Calender,
        BottomBarItem.Space,
        BottomBarItem.Gallery,
        BottomBarItem.Setting,
    )

    val isShowBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in bottomBarList.map { it.route }

    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.image_theme_default),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            scaffoldState = scaffoldState,
            bottomBar = {
                if (isShowBottomBar) {
                    BottomBar(navController = navController, listBarItem = bottomBarList)
                }
            },
            floatingActionButton = {
                if (isShowBottomBar) {
                    FloatingActionButton(
                        modifier = Modifier.border(
                            width = 4.dp,
                            color = Color.White,
                            shape = CircleShape
                        ),
                        onClick = { navController.navigate(GRAPH.DIARY) },
                        backgroundColor = Primary
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,

            ) {
            NavigationHost(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyDiaryTheme {
        MyDiary()
    }
}