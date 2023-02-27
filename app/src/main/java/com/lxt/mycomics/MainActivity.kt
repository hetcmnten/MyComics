package com.lxt.mycomics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lxt.mycomics.ui.theme.MyComicsTheme
import com.lxt.mycomics.view.CharacterBottomNav
import com.lxt.mycomics.view.CharacterDetailScreen
import com.lxt.mycomics.view.CollectionScreen
import com.lxt.mycomics.view.LibraryScreen
import com.lxt.mycomics.viewmodel.CollectionViewModel
import com.lxt.mycomics.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Library : Destination("library")
    object Collection : Destination("collection")
    object CharacterDetail : Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val lvm by viewModels<LibraryViewModel>()
    private val cvm by viewModels<CollectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComicsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController, lvm, cvm)
                }
            }
        }
    }
}

@Composable
fun CharactersScaffold(
    navController: NavHostController,
    lvm: LibraryViewModel,
    cvm: CollectionViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { CharacterBottomNav(navHostController = navController) },
    ) { paddingvalues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController, lvm, paddingvalues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                if (id == null) {
                    Toast.makeText(ctx, "Character id is required", Toast.LENGTH_SHORT).show()
                } else {
                    lvm.retrieveSingleCharacter(id)
                    CharacterDetailScreen(
                        lvm = lvm,
                        cvm = cvm,
                        paddingValues = paddingvalues,
                        navController = navController
                    )
                }
            }
        }
    }
}