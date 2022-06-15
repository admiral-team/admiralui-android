package com.admiral.demo.features.home.camera

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.FmtCameraBinding
import com.admiral.demo.ext.PermissionLauncher
import com.admiral.demo.ext.PermissionResult
import com.admiral.demo.features.main.NavigationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors

// TODO refactor this bad practise code (implement navigation result listener)
object CameraResultContainer {
    var result: String = ""
}

class CameraFragment : Fragment(R.layout.fmt_camera) {
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCameraBinding::bind)
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private val bankCardNumberAnalyzer = BankCardNumberAnalyzer(
        onSuccess = {
            CameraResultContainer.result = it
            navigationViewModel.close()
        },
        onFailure = {
            CameraResultContainer.result = ""
            navigationViewModel.close()
        }
    )

    private val permissionLauncher = PermissionLauncher.init(
        fragment = this,
        permission = Manifest.permission.CAMERA,
        resultAction = { result ->
            when (result) {
                is PermissionResult.Granted -> {
                    startCamera()
                }
                is PermissionResult.NotGranted -> {
                    lifecycleScope.launch {
                        delay(DELAY_IN_MILLISECONDS_FOR_SMOOTH_DIALOG_CLOSING)
                        navigationViewModel.close()
                    }
                }
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CameraResultContainer.result = ""
        permissionLauncher.request()
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(binding.preview.surfaceProvider) }

            val imageAnalysis = ImageAnalysis.Builder()
                // This guarantees only one image will be delivered for analysis at a time
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { imageAnalysis ->
                    imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        bankCardNumberAnalyzer.analyze(imageProxy)
                    }
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)

            } catch(exc: Exception) {
                Timber.e("Use case binding failed: $exc")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    companion object {
        private const val DELAY_IN_MILLISECONDS_FOR_SMOOTH_DIALOG_CLOSING = 300L
    }
}