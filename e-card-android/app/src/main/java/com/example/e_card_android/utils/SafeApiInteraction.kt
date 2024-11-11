package com.example.e_card_android.utils

import com.example.e_card_android.R
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    resourceProvider: ResourceProvider,
    onError: (String) -> Unit,
    block: suspend () -> T
): T? {
    return try {
        block()
    } catch (e: IOException) {
        onError(resourceProvider.getString(R.string.no_internet_connection_please_check_your_network_and_try_again))
        null
    } catch (e: TimeoutCancellationException) {
        onError(resourceProvider.getString(R.string.request_timed_out_please_try_again))
        null
    } catch (e: UnknownHostException) {
        onError(resourceProvider.getString(R.string.unable_to_reach_the_server_please_check_your_internet_connection))
        null
    } catch (e: Exception) {
        onError(resourceProvider.getString(R.string.an_unexpected_error_occurred_please_try_again))
        null
    }
}


