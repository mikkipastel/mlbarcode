package com.mikkipastel.mlbarcode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.mikkipastel.mlbarcode.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMlKit.setOnClickListener {
            val intent = Intent(this, BarcodeScannerActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGoogleScanner.setOnClickListener {
            openGoogleCodeScanner()
        }
    }

    private fun openGoogleCodeScanner() {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .enableAutoZoom()
            .build()
        val scanner = GmsBarcodeScanning.getClient(this, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                // Task completed successfully
                val rawValue: String? = barcode.rawValue
                Log.d("addOnSuccessListener", rawValue.toString())
                Toast.makeText(this, rawValue, Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                // Task canceled
                Log.d("addOnCanceledListener", "")
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                Log.d("addOnFailureListener", e.message.toString())
            }
    }
}