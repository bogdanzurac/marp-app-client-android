package dev.bogdanzurac.marp.core.ws

import kotlinx.datetime.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

class CustomInstantSerializer : KSerializer<Instant> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CustomInstant", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant =
        formatter.parse(decoder.decodeString(), java.time.LocalDateTime::from)
            .toKotlinLocalDateTime()
            .toInstant(TimeZone.UTC)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    private val formatter = DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE)
        .appendLiteral(" ")
        .append(DateTimeFormatter.ISO_LOCAL_TIME)
        .toFormatter()
}