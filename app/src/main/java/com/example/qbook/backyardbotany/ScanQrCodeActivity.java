package com.example.qbook.backyardbotany;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult.getBarcodeFormat().toString().equals("QR_CODE")) {
            if (rawResult.getText() != null) {
                Log.d("scanned: ", rawResult.getText());
                Intent intent = new Intent();
                intent.putExtra("qr_code_text", rawResult.getText());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            mScannerView.resumeCameraPreview(this);
        }
    }
}