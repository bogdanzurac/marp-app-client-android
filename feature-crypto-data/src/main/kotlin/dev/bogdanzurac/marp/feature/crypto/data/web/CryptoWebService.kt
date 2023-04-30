package dev.bogdanzurac.marp.feature.crypto.data.web

import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

/**
 * https://docs.coincap.io/
 */
@Single
internal class CryptoWebService(
    @Property(CRYPTO_KEY) val apiKey: String,
    featureManager: FeatureManager
) : WebService(
    "https://api.coincap.io",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        headers { bearerAuth(apiKey) }
    }

    suspend fun getCryptoAssets(): Result<List<CryptoAssetModel>> =
        get<CryptoAssetsModel>("/v2/assets")
            .map { it.list }
}

const val CRYPTO_KEY = "crypto_api_key"
