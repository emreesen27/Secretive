package com.sn.biometric

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors

class Biometric constructor(
    private val context: Context
) {

    private val authenticators = BIOMETRIC_STRONG or DEVICE_CREDENTIAL or BIOMETRIC_WEAK
    private val handler = Handler(Looper.getMainLooper())
    private var biometricListener: BiometricListener? = null

    private val biometricManager: BiometricManager? by lazy {
        BiometricManager.from(context)
    }

    //BIOMETRIC_ERROR_NO_HARDWARE,BIOMETRIC_ERROR_UNSUPPORTED
    @TargetApi(Build.VERSION_CODES.M)
    fun hasFingerprintEnrolled() = when (biometricManager?.canAuthenticate(authenticators)) {
        BIOMETRIC_SUCCESS -> true
        else -> false
    }


    @TargetApi(Build.VERSION_CODES.M)
    fun subscribe(listener: BiometricListener) {
        biometricListener = listener
    }

    fun unSubscribe() {
        biometricListener = null
    }

    fun showDialog(
        activity: FragmentActivity,
        strings: DialogStrings
    ) {
        handler.postDelayed({
            showBiometricPrompt(activity, strings)
        }, 250)
    }

    private fun showBiometricPrompt(
        activity: FragmentActivity,
        strings: DialogStrings
    ) {

        val (title, subtitle, description) = strings

        val prompt = BiometricPrompt(
            activity,
            Executors.newSingleThreadExecutor(),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    activity.runOnUiThread {
                        biometricListener?.onFingerprintAuthenticationFailure(
                            getErrorMessage(
                                errorCode
                            ), errorCode
                        )
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    activity.runOnUiThread {
                        biometricListener?.onFingerprintAuthenticationSuccess()
                    }
                }

                override fun onAuthenticationFailed() {
                    activity.runOnUiThread {
                        biometricListener?.onFingerprintAuthenticationFailure(
                            getErrorMessage(ERROR_NOT_RECOGNIZED),
                            ERROR_NOT_RECOGNIZED
                        )
                    }
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)
            .build()

        prompt.authenticate(promptInfo)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            BiometricPrompt.ERROR_HW_NOT_PRESENT,
            BiometricPrompt.ERROR_HW_UNAVAILABLE -> context.resources.getString(R.string.error_hw_unavailable)
            BiometricPrompt.ERROR_UNABLE_TO_PROCESS -> context.resources.getString(R.string.error_unable_to_process)
            BiometricPrompt.ERROR_TIMEOUT -> context.resources.getString(R.string.error_time_out)
            BiometricPrompt.ERROR_NO_SPACE -> context.resources.getString(R.string.error_no_space)
            BiometricPrompt.ERROR_CANCELED -> context.resources.getString(R.string.error_canceled)
            BiometricPrompt.ERROR_LOCKOUT -> context.resources.getString(R.string.error_lockout)
            BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> context.resources.getString(R.string.error_lockout_permanent)
            BiometricPrompt.ERROR_USER_CANCELED -> context.resources.getString(R.string.error_uer_canceled)
            ERROR_NOT_RECOGNIZED -> context.resources.getString(R.string.error_not_recognized)
            BiometricPrompt.ERROR_NO_BIOMETRICS -> context.resources.getString(R.string.error_no_biometrics)
            else -> context.resources.getString(R.string.error_unknown)
        }
    }

    companion object {
        const val ERROR_NOT_RECOGNIZED = -999
    }

    data class DialogStrings @JvmOverloads constructor(
        @JvmField val title: String,
        @JvmField val subTitle: String? = null,
        @JvmField val description: String? = null,
    )
}