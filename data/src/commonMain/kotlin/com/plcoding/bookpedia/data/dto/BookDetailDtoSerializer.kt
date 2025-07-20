package com.plcoding.bookpedia.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object BookDetailDtoSerializer : KSerializer<BookDetailDto> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        checkNotNull(BookDetailDto::class.simpleName),
    ) {
        element<String?>("description")
    }

    override fun deserialize(decoder: Decoder): BookDetailDto =
        decoder.decodeStructure(descriptor) {
            var description: String? = null
            while (true) {
                when (decodeElementIndex(descriptor)) {
                    0 -> {
                        val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("only json supported")
                        val element = jsonDecoder.decodeJsonElement()
                        description = if (element is JsonObject) {
                            decoder.json.decodeFromJsonElement(
                                element = element,
                                deserializer = BookDescriptionDto.serializer(),
                            ).value
                        } else if (element is JsonPrimitive && element.isString) {
                            element.content
                        } else null
                    }

                    CompositeDecoder.DECODE_DONE -> break

                    else -> {
                        throw SerializationException("Unexpected field index while decoding response")
                    }
                }
            }
            BookDetailDto(description = description)
        }

    override fun serialize(encoder: Encoder, value: BookDetailDto) =
        encoder.encodeStructure(descriptor) {
            value.description?.let {
                encodeStringElement(descriptor, 0, it)
            }
        }
}
