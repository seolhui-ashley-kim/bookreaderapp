package com.seolhui.bookreaderapp.screens.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.seolhui.bookreaderapp.compnents.ReaderAppBar
import com.seolhui.bookreaderapp.model.MBook
import com.seolhui.bookreaderapp.screens.home.HomeScreenViewModel
import com.seolhui.bookreaderapp.utils.formatDate
import com.google.firebase.auth.FirebaseAuth
import com.seolhui.bookreaderapp.R
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = stringResource(id = R.string.book_stats),
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        },
    ) {
        Surface() {
            //only show books by this user that have been read
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mbook ->
                    (mbook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .padding(2.dp)
                    ) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = stringResource(
                            id = R.string.person_icon
                        ))
                    }
                    Text(
                        text = String.format(stringResource(id = R.string.hi_user), currentUser?.email.toString().split("@")[0])
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = 5.dp
                ) {

                    val readBooksList: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        } else {
                            emptyList()
                        }
                    val readingBooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(id = R.string.your_stats), style = MaterialTheme.typography.h5)
                        Divider()
                        Text(text = String.format(stringResource(id = R.string.you_are_reading), readBooksList.size))
                        Text(text = String.format(stringResource(id = R.string.you_have_read), readBooksList.size))
                    }
                }

                if (viewModel.data.value.loading == true) {
                    LinearProgressIndicator()
                } else {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        //filter books by finished ones
                        val readBooks: List<MBook> =
                            if (!viewModel.data.value.data.isNullOrEmpty()) {
                                viewModel.data.value.data!!.filter { mBook ->
                                    (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                                }
                            } else {
                                emptyList()
                            }
                        items(items = readBooks) { book ->
                            BookRowStats(book = book)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookRowStats(book: MBook) {
    Card(modifier = Modifier
        .clickable {
            //navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp), shape = RectangleShape, elevation = 7.dp) {
        Row(
            modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top
        ) {
            val imageUrl: String =
                book.photoUrl.toString().ifEmpty { "http://books.google.com/books/content?id=LY1FDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api" }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = stringResource(R.string.book_image),
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(4.dp)
            )
            Column {

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.thumbs_up),
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    } else{
                        //TODO: update further
                        Box {}
                    }
                }
                Text(
                    text = String.format(stringResource(R.string.title_author), book.authors),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = String.format(stringResource(R.string.title_started), book.startedReading),
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )

                Text(
                    text = String.format(stringResource(R.string.title_finished), book.finishedReading),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
