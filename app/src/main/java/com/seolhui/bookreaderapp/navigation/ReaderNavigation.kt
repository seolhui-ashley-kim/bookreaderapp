package com.seolhui.bookreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.seolhui.bookreaderapp.screens.ReaderSplashScreen
import com.seolhui.bookreaderapp.screens.details.BookDetailsScreen
import com.seolhui.bookreaderapp.screens.home.Home
import com.seolhui.bookreaderapp.screens.home.HomeScreenViewModel
import com.seolhui.bookreaderapp.screens.login.ReaderLoginScreen
import com.seolhui.bookreaderapp.screens.search.BookSearchViewModel
import com.seolhui.bookreaderapp.screens.search.SearchScreen
import com.seolhui.bookreaderapp.screens.stats.ReaderStatsScreen
import com.seolhui.bookreaderapp.screens.update.BookUpdateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.SearchScreen.name) {

            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailsName = ReaderScreens.DetailScreen.name
        composable("$detailsName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) { backstackEntry ->
            backstackEntry.arguments?.getString("bookId").let {

                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable(
            "$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })
        ) {
            navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }


    }
}