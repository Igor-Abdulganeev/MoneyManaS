package com.friendroids.moneymana.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.friendroids.moneymana.data.camera.ManaCameraX
import com.friendroids.moneymana.databinding.FragmentCameraBinding
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraX: ManaCameraX
    private lateinit var executor: Executor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCameraBinding.inflate(inflater, container, false)
        .run {
            _binding = this
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        cameraX = ManaCameraX(requireContext(), executor, binding.cameraView, viewLifecycleOwner)
        binding.button.setOnClickListener {
            // takePhoto(this)
        }
        cameraX.bindCamera()
    }

    companion object {
        fun newInstance() = CameraFragment()
    }
}