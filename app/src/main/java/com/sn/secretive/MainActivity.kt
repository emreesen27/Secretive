package com.sn.secretive

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sn.secretive.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executor
import javax.inject.Inject
import androidx.navigation.fragment.NavHostFragment


import java.lang.System.exit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navigator: NavController
    private val vm: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    /*
    private lateinit var biometricManager: BiometricManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var enrollBiometricRequestLauncher: ActivityResultLauncher<Intent>
    private val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL or BIOMETRIC_WEAK*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        vm.userLiveData.observe(this) { user ->
            setNavigationGraph(if (user == null) R.id.PinFragment else R.id.LoginFragment)
        }

        /*val action = PinFragmentDirections.actionPinToLogin()
        navController.navigate(action)*/

        /*
        biometricManager = BiometricManager.from(this)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(
                this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        showSnackBar("Authentication error: Code: $errorCode ($errString)")
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        showSnackBar("Failed to authenticate. Please try again.")
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val type = result.authenticationType
                        showSnackBar("\uD83C\uDF89 Authentication successful! Type: $type \uD83C\uDF89")
                    }
                },
            )

        // Initialize PromptInfo to set title, subtitle, and authenticators of the biometric prompt
        try {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Example biometric authentication")
                .setSubtitle("Please authenticate yourself first.")
                .setAllowedAuthenticators(authenticators)
                .build()
        } catch (e: Exception) {
            showSnackBar(e.message ?: "Unable to initialize PromptInfo")
        }

        // Initialize a launcher for requesting user to enroll in biometric
        enrollBiometricRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    tryAuthenticateBiometric()
                } else {
                    showSnackBar("Failed to enroll in biometric")
                }
            }

        // Setup on click listener for button
        findViewById<Button>(R.id.button_biometric).setOnClickListener {
            tryAuthenticateBiometric()
        }
    }

    /**
     * Attempt to show biometric prompt dialog to user.
     */
    private fun tryAuthenticateBiometric() {
        checkDeviceCapability {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    /**
     * Check the device capability for biometric.
     *
     * If the device is capable, [onSuccess] will be called. Otherwise, a [Snackbar] is shown.
     */
    private fun checkDeviceCapability(onSuccess: () -> Unit) {
        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS, BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                onSuccess()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showSnackBar("No biometric features available on this device")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showSnackBar("Biometric features are currently unavailable")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                showSnackBar("Biometric options are incompatible with the current Android version")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    enrollBiometricRequestLauncher.launch(
                        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                authenticators
                            )
                        }
                    )
                } else {
                    showSnackBar("Could not request biometric enrollment in API level < 30")
                }
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                showSnackBar("Biometric features are unavailable because security vulnerabilities has been discovered in one or more hardware sensors")
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }*/
    }

    private fun setNavigationGraph(startDestId: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.base_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.base_nav_graph)
        navGraph.setStartDestination(startDestId)
        navController.graph = navGraph
    }
}