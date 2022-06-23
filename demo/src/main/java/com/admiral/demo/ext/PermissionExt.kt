package com.admiral.demo.ext

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

private const val PREF_KEY__WAS_PERMISSION_REQUESTED_RATIONALE = "PREF_KEY__WAS_PERMISSION_REQUESTED_RATIONALE"

private fun getSharedPreferencesPermissionKey(permission: String): String =
    "$PREF_KEY__WAS_PERMISSION_REQUESTED_RATIONALE $permission"

internal sealed class PermissionResult {
    object Granted : PermissionResult()
    data class NotGranted(
        val permission: String,
        val isGrantingPermissionInSettingsRequired: Boolean
    ) : PermissionResult()
}

private fun Fragment.getWasPermissionRequestedRationale(permission: String): Boolean {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    val key = getSharedPreferencesPermissionKey(permission)
    return sharedPreferences.getBoolean(key, false)
}

private fun Fragment.setWasPermissionRequestedRationale(permission: String) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
    val key = getSharedPreferencesPermissionKey(permission)
    sharedPreferences.edit().apply {
        putBoolean(key, true)
        apply()
    }
}

internal fun Fragment.isPermissionGranted(permission: String): Boolean {
    val result = ContextCompat.checkSelfPermission(requireContext(), permission)
    return result == PackageManager.PERMISSION_GRANTED
}

internal class PermissionLauncher private constructor(
    private val fragment: Fragment,
    private val permission: String,
    private val resultAction: (result: PermissionResult) -> Unit,
    private val requestPermissionLauncher: ActivityResultLauncher<String>
) {

    fun request() {
        if (fragment.isPermissionGranted(permission)) {
            resultAction(PermissionResult.Granted)
            return
        }

        if (fragment.shouldShowRequestPermissionRationale(permission)) {
            requestPermissionLauncher.launch(permission)
            fragment.setWasPermissionRequestedRationale(permission)
        } else {
            if (fragment.getWasPermissionRequestedRationale(permission)) {
                resultAction(
                    PermissionResult.NotGranted(
                        permission = permission,
                        isGrantingPermissionInSettingsRequired = true
                    )
                )
            } else {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    companion object {
        fun init(
            fragment: Fragment,
            permission: String,
            resultAction: (result: PermissionResult) -> Unit
        ): PermissionLauncher {

            val requestPermissionLauncher = fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    resultAction(PermissionResult.Granted)
                } else {
                    val isGrantingPermissionInSettingsRequired = fragment
                        .getWasPermissionRequestedRationale(permission)

                    resultAction(
                        PermissionResult.NotGranted(
                            permission = permission,
                            isGrantingPermissionInSettingsRequired = isGrantingPermissionInSettingsRequired
                        )
                    )
                }
            }

            return PermissionLauncher(
                fragment = fragment,
                permission = permission,
                resultAction = resultAction,
                requestPermissionLauncher = requestPermissionLauncher
            )
        }
    }
}