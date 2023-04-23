package dev.bogdanzurac.marp.app.elgoog.crypto

import CRYPTO_API_KEY
import dev.bogdanzurac.marp.core.feature.FeatureManager
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkDelayFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.MockNetworkErrorsFeature
import dev.bogdanzurac.marp.app.elgoog.core.ws.WebService
import io.ktor.client.request.*
import org.koin.core.annotation.Single

/**
 * https://docs.coincap.io/
 */
@Single
class CryptoWebService(featureManager: FeatureManager) : WebService(
    "https://api.coincap.io",
    featureManager.isEnabled(MockNetworkDelayFeature),
    featureManager.isEnabled(MockNetworkErrorsFeature)
) {

    override val requestBuilder: HttpRequestBuilder.() -> Unit = {
        headers { bearerAuth(CRYPTO_API_KEY) }
    }

    suspend fun getCryptoAssets(): Result<List<CryptoAssetModel>> =
        get<CryptoAssetsModel>("/v2/assets")
            .map { it.list }
}
