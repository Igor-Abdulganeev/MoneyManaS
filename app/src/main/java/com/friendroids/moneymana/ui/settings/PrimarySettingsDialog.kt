package com.friendroids.moneymana.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.repository.SettingsRepositoryImpl
import com.friendroids.moneymana.databinding.DialogPrimarySettingsBinding
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.ui.settings.viewmodel.PrimarySettingsViewModel
import com.friendroids.moneymana.ui.settings.viewmodel.PrimarySettingsViewModelFactory
import com.friendroids.moneymana.utils.extensions.changeColor

class PrimarySettingsDialog : DialogFragment() {

    private var _binding: DialogPrimarySettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PrimarySettingsViewModel by viewModels {
        PrimarySettingsViewModelFactory(
            SettingsRepositoryImpl(ManaDatabase.getInstance(requireContext().applicationContext))
        )
    }

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
        initViews()
        viewModel.settings.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.sumBudget != 0) {
                    binding.totalAmountEditText.setText(it.sumBudget.toString())
                }
                binding.revertPeriodEditText.setText(it.dayRestart.toString())
            }

        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        with(binding) {
            totalAmountEditText.addTextChangedListener { validateButton() }
            revertPeriodEditText.addTextChangedListener { validateButton() }
            saveSettingsButton.setOnClickListener {
                saveSettings(changePrimarySettings())
                dismiss()
            }
        }
    }

    private fun validateButton() {
        with(binding) {
            saveSettingsButton.isEnabled =
                totalAmountEditText.text?.isNotBlank() == true
                        && revertPeriodEditText.text?.isNotBlank() == true
                        && revertPeriodEditText.text.toString().toInt() > 0
                        && revertPeriodEditText.text.toString().toInt() < 32
            if (saveSettingsButton.isEnabled) saveSettingsButton.changeColor(
                    requireContext(),
                    R.color.primaryColor
            )
            else saveSettingsButton.changeColor(requireContext(), R.color.light_grey)
        }
    }

    private fun saveSettings(settings: TotalBudgetEntity) {
        viewModel.updateSettings(settings)
    }

    private fun changePrimarySettings() = TotalBudgetEntity(
        id = null,
        month = 4,
        year = 2021,
        dayRestart = binding.revertPeriodEditText.text.toString().toInt(),
        sumBudget = binding.totalAmountEditText.text.toString().toInt()
    )

    companion object {
        const val PRIMARY_SETTINGS_DIALOG = "PRIMARY_SETTINGS_DIALOG"
    }
}
