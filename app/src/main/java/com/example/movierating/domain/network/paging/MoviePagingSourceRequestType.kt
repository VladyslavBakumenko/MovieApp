package com.example.movierating.domain.network.paging

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MoviePagingSourceRequestType: Parcelable {
    GET_BY_POPULAR, GET_BY_TOP_RATED, GET_SEARCHED, GET_UPCOMING, GET_SIMILAR
}