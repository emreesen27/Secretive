package com.sn.biometric

interface BiometricListener {
    fun onFingerprintAuthenticationSuccess()
    fun onFingerprintAuthenticationFailure(errorMessage: String, errorCode: Int)
}