package com.friendroids.moneymana.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.friendroids.moneymana.R
import com.friendroids.moneymana.databinding.ActivityMainBinding
import com.friendroids.moneymana.ui.camera.CameraFragment
import com.friendroids.moneymana.ui.fragment_category.ManaCategoryFragment
import com.friendroids.moneymana.ui.mana_categories.ManaCategoriesFragment
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class MainActivity : AppCompatActivity(), NavigationActivity {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ManaCategoriesFragment(), MAIN_FRAGMENT)
                .commit()
        }

        binding.cameraButton.setOnClickListener {
            binding.cameraButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val foundFragments = supportFragmentManager.fragments
            if (foundFragments.count() > 0) {
                val fragment = foundFragments[0]
                if (fragment is CameraFragment) {
                    fragment.scan()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CameraFragment.newInstance(), CAMERA_FRAGMENT)
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                Log.d(TAG, "$TAG - что то пошло не так с фрагментами")
            }
        }
        binding.cameraButton.setImageResource(R.drawable.add_shopping_24)
    }

    override fun onResume() {
        super.onResume()
        if (allPermissionsGranted()) {
            //   TODO("можно кнопку сделать активной")
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun setImageResource(resId: Int) {
        binding.cameraButton.setImageResource(resId)
    }

    override fun openCategoryFragment(idCategory: Int) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, ManaCategoryFragment.newInstance(idCategory), MAIN_FRAGMENT)
            .commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                //   TODO("можно кнопку сделать активной")
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.camera_access_denyded_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val CAMERA_FRAGMENT = "CameraFragment"
        private const val MAIN_FRAGMENT = "MainFragment"
        private const val TAG = "MainActivity"
    }
}
