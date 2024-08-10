package com.shunm.infra.chat.datasource

import android.graphics.Bitmap
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import com.shunm.infra.chat.BuildConfig
import javax.inject.Inject

internal class GeminiRemoteDatasource
@Inject
constructor() {
    companion object {
        private const val modelName = "gemini-1.5-flash"
        private val config: GenerationConfig =
            generationConfig {
                temperature = 1f
                topK = 64
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "text/plain"
            }
        private val safetySettings =
            listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
            )
        private val systemInstruction: Content =
            content {
                text(
                    "You are a psychiatrist.\n\nKeep your answers under 3 paragraphs long, and use an upbeat, chipper tone\nin your answers.",
                )
            }
    }

    private var chat: Chat

    init {
        val model =
            GenerativeModel(
                modelName = modelName,
                apiKey = BuildConfig.GEMINI_API_KEY,
                generationConfig = config,
                safetySettings = safetySettings,
                systemInstruction = systemInstruction,
            )
        chat = model.startChat()
    }

    suspend fun sendMessage(
        message: String,
        imageList: List<Bitmap>,
    ): ExceptionResult<String> {
        val prompt =
            content {
                text(message)
                imageList.forEach { image ->
                    image(image)
                }
            }
        val response = chat.sendMessage(prompt)
        return Ok(response.text ?: "")
    }
}
