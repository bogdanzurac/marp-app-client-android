package dev.bogdanzurac.marp.app.elgoog.core.ws

import dev.bogdanzurac.marp.core.exception.NetworkException
import dev.bogdanzurac.marp.core.exception.NotAvailableException
import dev.bogdanzurac.marp.core.exception.UnknownApiResponseException
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.recoverRethrow
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.io.IOException
import kotlin.random.Random

abstract class WebService(
    val baseUrl: String,
    val mockNetworkDelay: Boolean = false,
    val mockNetworkErrors: Boolean = false,
) {

    open val requestBuilder: HttpRequestBuilder.() -> Unit = {}

    protected suspend inline fun <reified T> get(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> = request(url, HttpMethod.Get, block)

    protected suspend inline fun <reified T> post(
        url: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> = request(url, HttpMethod.Post, block)

    suspend inline fun <reified T> request(
        url: String,
        httpMethod: HttpMethod,
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> =
        runCatching<T> {
            val response = client.request(baseUrl + url) {
                requestBuilder()
                method = httpMethod
                block()
            }
            val logMessage =
                "${httpMethod.value} ${response.request.url} returned HTTP ${response.status.value}: ${response.bodyAsText()}"
            if (response.status.isSuccess()) logger.d(logMessage)
            else logger.e(logMessage)
            response.body()
        }.recoverRethrow {
            logger.e(it.message ?: "Network error", it)
            when (it) {
                // JSON parsing issues
                is ContentConvertException -> NetworkException()
                // Network problems
                is IOException -> NetworkException()
                // Web service API errors
                is ClientRequestException -> {
                    if (it.response.status == HttpStatusCode.NotFound) NotAvailableException
                    else UnknownApiResponseException()
                }
                // This shouldn't trigger
                else -> it
            }
        }.mapCatching {
            // emulate slow network conditions
            if (mockNetworkDelay) mockNetworkDelay(baseUrl + url)
            // emulate a random network error sometimes
            if (mockNetworkErrors && Random.nextBoolean()) {
                logger.e("Mocking network error for: $url")
                throw NetworkException()
            }
            it
        }
}

val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}