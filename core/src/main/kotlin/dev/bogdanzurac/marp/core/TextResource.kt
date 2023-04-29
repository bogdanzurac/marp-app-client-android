package dev.bogdanzurac.marp.core

/**
 * Abstract representation of a Text. Various implementations wrap different text sources
 * (e.g. pure strings, texts pulled from localized string resources etc.).
 */
sealed interface TextResource {

    companion object {

        /**
         * Creates a [TextResource] based on a localized string resource.
         */
        operator fun invoke(resId: Int, vararg args: Any): TextResource =
            StringIdTextResource(resId, args)
    }

    class StringIdTextResource(
        val stringResId: Int,
        val args: Array<out Any>,
    ) : TextResource
}
