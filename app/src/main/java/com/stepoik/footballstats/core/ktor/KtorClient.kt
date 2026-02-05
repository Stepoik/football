package com.stepoik.footballstats.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun defaultKtorClient(): HttpClient {
    return HttpClient(OkHttp) {
        commonConfig()
    }
}

internal val KtorJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.commonConfig() {
    install(ContentNegotiation) {
        json(KtorJson)
    }
    HttpResponseValidator {
        validateResponse { response ->
            val statusCode = response.status
            if (statusCode.value in 400..499 && statusCode != HttpStatusCode.Unauthorized) {
                throw ClientRequestException(response, "Client error ${statusCode.value}")
            }
            if (statusCode.value >= 500) {
                throw ServerResponseException(response, "Server error ${statusCode.value}")
            }
        }

        handleResponseExceptionWithRequest { exception, _ ->
            println("HTTP Exception: $exception")
        }
    }

    defaultRequest {
        contentType(ContentType.Application.Json)
    }

    install(HttpTimeout) {
        connectTimeoutMillis = 10_000
        requestTimeoutMillis = 10_000
    }
}