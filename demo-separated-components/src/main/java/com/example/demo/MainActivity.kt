package com.example.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.admiral.uikit.notification.action.ActionNotification
import com.admiral.uikit.notification.action.ActionNotificationCloseType
import com.admiral.uikit.notification.toast.ToastNotification
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnAction.setOnClickListener {
            binding.staticNotificationContainer.isVisible = false
            ActionNotification.Builder(this, binding.coordinator)
                .setText(getString(R.string.notifications_action_action_text))
                .setDuration(DURATION)
                .setMargins(bottom = MARGIN)
                .setCloseButtonType(
                    ActionNotificationCloseType.Text(
                        text = "Cancel"
                    )
                )
                .build()
                .show()
        }

        binding.btnStatic.setOnClickListener {
            binding.staticNotificationContainer.isVisible = true
        }

        binding.btnToast.setOnClickListener {
            binding.staticNotificationContainer.isVisible = false
            val multipleToastNotification =
                ToastNotification.Builder(this, binding.coordinator)
                    .setLinkText("Link text")
                    .setLinkClickListener {
                        it.hide()
                    }
                    .setIsWidthMatchParent(true)
                    .setMargins(top = 20)
                    .setText("At breakpoint boundaries, mini units divide the screen into a fixed master grid.")
                    .apply()

            multipleToastNotification.show()
        }
    }

    private companion object {
        const val DURATION = 10000
        const val MARGIN = 70
    }
}