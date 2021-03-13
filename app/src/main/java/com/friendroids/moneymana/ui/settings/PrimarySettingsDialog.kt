package com.friendroids.moneymana.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.friendroids.moneymana.databinding.DialogPrimarySettingsBinding

class PrimarySettingsDialog : DialogFragment() {

    private var _binding: DialogPrimarySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPrimarySettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        initView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        binding.saveSettingsButton.setOnClickListener {
            changePrimarySettings()
            //todo check if date was changed - replace periodic work request
            dismiss()
        }
    }

    private fun changePrimarySettings() {
        val amount = binding.totalAmountEditText.text.toString()
        val period = binding.revertPeriodEditText.text.toString()
    }

    private fun saveToSharedPrefs() {
        //todo
    }

    companion object {
        const val PRIMARY_SETTINGS_DIALOG = "PRIMARY_SETTINGS_DIALOG"
    }
}
