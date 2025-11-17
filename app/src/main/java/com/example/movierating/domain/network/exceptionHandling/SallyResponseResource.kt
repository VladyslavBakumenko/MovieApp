package com.example.movierating.domain.network.exceptionHandling

sealed interface SallyResponseResource<out T> {
    data class Success<T>(val data: T) : SallyResponseResource<T>
    data class Error(val exception: AppException, val errorCode: String? = null) :
        SallyResponseResource<Nothing>

    data class Loading(val status: Boolean) : SallyResponseResource<Nothing>
}

open class AppException(message: String? = null, cause: Throwable? = null) :
    Throwable(message, cause)

class NetworkException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ServerException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ClientException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class UnknownException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)