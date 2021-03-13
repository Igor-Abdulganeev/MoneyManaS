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
import com.friendroids.moneymana.db.DBContract.TotalBudget.TOTAL_BUDGET_PRIMARY_KEY
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.ui.settings.viewmodel.PrimarySettingsViewModel
import com.friendroids.moneymana.ui.settings.viewmodel.PrimarySettingsViewModelFactory
import com.friendroids.moneymana.utils.extensions.changeColor

class PrimarySettingsDialog : DialogFragment() {

    private var _binding: DialogPrimarySettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PrimarySettingsViewModel by viewModels {
        PrimarySettingsViewModelFactory(
            SettingsRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
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
            binding.totalAmountEditText.setText(it.sum.toString())
            binding.revertPeriodEditText.setText(it.daysTillRestartCount.toString())
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
                totalAmountEditText.text.isNotBlank() && revertPeriodEditText.text.isNotBlank()
            if (saveSettingsButton.isEnabled) saveSettingsButton.changeColor(
                requireContext(),
                R.color.bright_yellow
            )
            else saveSettingsButton.changeColor(requireContext(), R.color.button_disabled_grey)
        }
    }

    private fun saveSettings(settings: TotalBudgetEntity) {
        viewModel.updateSettings(settings)
    }

    private fun changePrimarySettings() = TotalBudgetEntity(
        id = TOTAL_BUDGET_PRIMARY_KEY,
        sum = binding.totalAmountEditText.text.toString().toInt(),
        daysTillRestartCount = binding.revertPeriodEditText.text.toString().toInt()
        //todo check if date was changed - replace periodic work request
    )

    companion object {
        const val PRIMARY_SETTINGS_DIALOG = "PRIMARY_SETTINGS_DIALOG"
    }
}
