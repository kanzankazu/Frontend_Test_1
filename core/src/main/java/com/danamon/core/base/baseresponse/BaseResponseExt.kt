package com.danamon.core.base.baseresponse

fun initBaseResponseLoading() = BaseResponse.Loading
fun <T> T.toBaseResponseSuccess() = BaseResponse.Success(this)
fun String.toBaseResponseError(errorMessage: String = "") = BaseResponse.Error(errorMessage.ifEmpty { this })

fun <T> BaseResponse<T>.handleBaseResponse(
    onLoading: ((Boolean) -> Unit)? = null,
    onEmpty: (() -> Unit)? = null,
    onError: ((String) -> Unit)? = null,
    onSuccess: (T) -> Unit,
) {
    when (this) {
        is BaseResponse.Loading -> {
            onLoading?.invoke(true)
        }

        is BaseResponse.Empty -> {
            onLoading?.invoke(false)
            onEmpty?.invoke()
        }

        is BaseResponse.Success -> {
            onLoading?.invoke(false)
            onSuccess.invoke(data)
        }

        is BaseResponse.Error -> {
            onLoading?.invoke(false)
            val error = when (this) {
                is HttpError -> message
                is NoInternetError, is TimeOutError -> "Connection lost. Check your internet and try again, okay?"
                else -> message.ifEmpty { "OOOooopppsss 😤" }
            }
            onError?.invoke(error)
        }
    }
}

fun <T, R> BaseResponse<T>.handleBaseResponseConvert(
    onSuccess: (T) -> BaseResponse<R>,
): BaseResponse<R> = when (this) {
    is BaseResponse.Loading -> BaseResponse.Loading
    is BaseResponse.Empty -> BaseResponse.Empty
    is BaseResponse.Success -> onSuccess.invoke(this.data)
    is BaseResponse.Error -> BaseResponse.Error(this.message)
}

fun <T, R> BaseResponse<T>.handleBaseResponseConvertData(
    onError: (String) -> String = { "" },
    onSuccess: (T) -> R,
): BaseResponse<R> = when (this) {
    is BaseResponse.Loading -> BaseResponse.Loading
    is BaseResponse.Empty -> BaseResponse.Empty
    is BaseResponse.Success -> BaseResponse.Success(onSuccess.invoke(this.data))
    is BaseResponse.Error -> BaseResponse.Error(onError.invoke("").ifEmpty { this.message })
}

