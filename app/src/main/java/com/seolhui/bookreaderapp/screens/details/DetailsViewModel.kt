package com.seolhui.bookreaderapp.screens.details

import androidx.lifecycle.ViewModel
import com.seolhui.bookreaderapp.data.Resource
import com.seolhui.bookreaderapp.model.Item
import com.seolhui.bookreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}