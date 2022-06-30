package com.admiral.demo.features.home.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class BankCardNumberAnalyzer(
    private val onSuccess: (String) -> Unit = {},
    private val onFailure: (String) -> Unit = {}
) : ImageAnalysis.Analyzer  {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val bankCardRegex = Regex(BANK_CARD_REGEX)

    private companion object {
        private const val BANK_CARD_REGEX = "[0-9 ]{19}"
    }

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            recognizer.process(image)
                .addOnSuccessListener() { visionText ->

                    visionText.textBlocks.forEach { textBlock ->
                        textBlock.lines.forEach { textLine ->
                            bankCardRegex.find(textLine.text)?.let {
                                onSuccess(it.value)
                            }
                        }
                    }
                    imageProxy.close()
                }
                .addOnFailureListener() { e ->
                    onFailure(e.localizedMessage ?: "Image analyze error")
                    imageProxy.close()
                }
        }
    }
}