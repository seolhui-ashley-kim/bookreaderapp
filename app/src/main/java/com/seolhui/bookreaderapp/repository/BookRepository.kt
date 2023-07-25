package com.seolhui.bookreaderapp.repository

import com.seolhui.bookreaderapp.data.Resource
import com.seolhui.bookreaderapp.model.Item
import com.seolhui.bookreaderapp.network.BooksApi
import javax.inject.Inject


class BookRepository @Inject constructor(private val api: BooksApi) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)

        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }

    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)

        } catch (exception: Exception) {
            return Resource.Error(message = "An Error Occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}


