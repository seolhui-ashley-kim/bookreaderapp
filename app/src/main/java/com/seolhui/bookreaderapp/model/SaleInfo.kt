package com.seolhui.bookreaderapp.model

import com.seolhui.bookreaderapp.model.ListPrice
import com.seolhui.bookreaderapp.model.Offer
import com.seolhui.bookreaderapp.model.RetailPriceX

data class SaleInfo(
    val buyLink: String,
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice,
    val offers: List<Offer>,
    val retailPrice: RetailPriceX,
    val saleability: String
)