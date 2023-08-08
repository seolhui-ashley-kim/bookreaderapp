package com.seolhui.bookreaderapp.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.seolhui.bookreaderapp.R
import com.seolhui.bookreaderapp.compnents.InputField
import com.seolhui.bookreaderapp.compnents.ReaderAppBar
import com.seolhui.bookreaderapp.model.Item
import com.seolhui.bookreaderapp.navigation.ReaderScreens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController, viewModel: BookSearchViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = stringResource(id = R.string.search),
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) { searchQuery ->
                    viewModel.searchBooks(query = searchQuery)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController)

            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel = hiltViewModel()) {


    val listOfBooks = viewModel.list
    if (viewModel.isLoading) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            LinearProgressIndicator()
            Text(text = stringResource(id = R.string.loading_text))
        }
    } else {

        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }
        }
    }
}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp), shape = RectangleShape, elevation = 7.dp) {
        Row(
            modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top
        ) {
            val imageUrl: String =
                book.volumeInfo.imageLinks.smallThumbnail.ifEmpty { "http://books.google.com/books/content?id=LY1FDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api" }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = stringResource(id = R.string.book_image),
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
            )
            Column(
            ) {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
                Text(
                    text = String.format(stringResource(id = R.string.title_author), book.volumeInfo.authors),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = String.format(stringResource(id = R.string.title_date), book.volumeInfo.publishedDate),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = "${book.volumeInfo.categories}", //TODO: upate further
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = stringResource(id = R.string.search),
    onSearch: (String) -> Unit = {}
) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) { searchQueryState.value.trim().isNotEmpty() }

        InputField(valueState = searchQueryState,
            labelId = stringResource(id = R.string.search),
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}
