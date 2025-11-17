import com.example.movierating.domain.network.exceptionHandling.AppException
import com.example.movierating.domain.network.exceptionHandling.ClientException
import com.example.movierating.domain.network.exceptionHandling.NetworkException
import com.example.movierating.domain.network.exceptionHandling.SallyResponseResource
import com.example.movierating.domain.network.exceptionHandling.ServerException
import com.example.movierating.domain.network.exceptionHandling.UnknownException
import com.example.movierating.domain.utils.CLIENT_ERROR
import com.example.movierating.domain.utils.HTTP_UNKNOWN_ERROR
import com.example.movierating.domain.utils.NETWORK_ERROR
import com.example.movierating.domain.utils.SERVER_ERROR
import com.example.movierating.domain.utils.UNKNOWN_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException

fun <T> Flow<T>.asSallyResponseResourceFlow(): Flow<SallyResponseResource<T>> {
    return this
        .map<T, SallyResponseResource<T>> {
            SallyResponseResource.Success(it)
        }
        .onStart { emit(SallyResponseResource.Loading(true)) }
        .onCompletion { emit(SallyResponseResource.Loading(false)) }
        .catch { error ->
            val exception = when (error) {
                is HttpException -> {
                    when (error.code()) {
                        in 400..499 -> {
                            ClientException(
                                message = "${CLIENT_ERROR}: ${error.code()}",
                                cause = error,
                            )
                        }

                        in 500..599 -> {
                            ServerException(
                                message = "${SERVER_ERROR}: ${error.code()}",
                                cause = error
                            )
                        }

                        else -> {
                            UnknownException(
                                message = "${HTTP_UNKNOWN_ERROR}: ${error.code()}",
                                cause = error
                            )
                        }
                    }
                }

                is IOException -> NetworkException(
                    message = NETWORK_ERROR,
                    cause = error
                )

                else -> AppException(
                    message = UNKNOWN_ERROR,
                    cause = error
                )
            }

            val errorCode = when (error) {
                is HttpException -> {
                    when (error.code()) {
                        in 400..499 -> {
                            "#ER${error.code()}"
                        }

                        in 500..599 -> {
                            "#ER${error.code()}"
                        }

                        else -> {
                            "#ER${error.code()}"
                        }
                    }
                }

                else -> {
                    error.cause?.message.toString()
                }
            }
            emit(SallyResponseResource.Error(exception, errorCode))
        }
}