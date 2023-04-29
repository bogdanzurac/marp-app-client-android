package dev.bogdanzurac.marp.core.ws

import dev.bogdanzurac.marp.core.logger
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/**
 * Mock network delay between 100-1000ms.
 */
suspend fun mockNetworkDelay(url: String) {
    logger.d("Mocking network delay for: $url")
    delay(Random.nextInt(100, 1000).milliseconds)
}