package com.tuwaiq.AndroidGeeks

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.tuwaiq.AndroidGeeks.ui.dashboard.UserPreference

class MainActivityForTesting : AppCompatActivity() {

    private var cancellationSignal:CancellationSignal?=null
    private val darkModeSwitch get() = this?.let { UserPreference.loadNightModeState(it) }


    private val authenticationCallback:BiometricPrompt.AuthenticationCallback
    get() =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Authentication error : $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication success")
                val intent = Intent(this@MainActivityForTesting, MainActivity::class.java)
                startActivity(intent)

            }
        }

    override fun onStart() {
        super.onStart()
            checkBiometricSupport()
            fingerPrint()

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_for_testing)
    }

    private fun fingerPrint() {
        if (FirebaseAuth.getInstance().currentUser?.uid != null) {
            Log.d("uid", "onCreate: ${FirebaseAuth.getInstance().currentUser?.uid}")
            val biometricPrompt: BiometricPrompt = BiometricPrompt.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setSubtitle(getString(R.string.authentication_is_required_finger))
                .setDescription(getString(R.string.uses_fingerprint_finger))
                .setNegativeButton(
                    getString(R.string.cancel_finger),
                    this.mainExecutor,
                    DialogInterface.OnClickListener { dialog, which ->
                        notifyUser(getString(R.string.authentication_cancelled_finger))
                    }).build()
            biometricPrompt.authenticate(CancellationSignal(), mainExecutor, authenticationCallback)
        }
    }

    private fun notifyUser(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun checkBiometricSupport():Boolean{

        val keyguardManager:KeyguardManager =getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure){
            notifyUser(getString(R.string.authentication_has_not_been_enabled))
            return false


        }
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            notifyUser(getString(R.string.finger_printy))
            return false
        }
        return if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }

    override fun onBackPressed() {

    }
}