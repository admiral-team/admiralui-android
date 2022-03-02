package com.admiral.demo.features.info

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInfoContactsBinding
import com.admiral.demo.features.main.NavigationViewModel

class ContactsFragment : BaseFragment(R.layout.fmt_info_contacts) {

    private val binding by viewBinding(FmtInfoContactsBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        binding.supportLink.text = "@digitalframeworks_mobilebot"
        binding.f1.text = "Алырщикова Дарья"
        binding.f2.text = "alyrchshikova@gmail.com"
        binding.f3.text = "Поляков Антон"
        binding.f4.text = "ANPolyakov@innotechnum.com"

        binding.supportLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=digitalframeworks_mobilebot"))
            startActivity(intent)
        }

        binding.f2.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("", binding.f2.text))

            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.info_contacts_copied),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.f4.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("", binding.f4.text))

            Toast.makeText(
                requireContext(),
                requireContext().getString(R.string.info_contacts_copied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}