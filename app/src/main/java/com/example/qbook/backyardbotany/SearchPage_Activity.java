package com.example.qbook.backyardbotany;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class SearchPage_Activity extends AppCompatActivity {
    private EditText editText;
    private DatabaseFunctionalityClass databaseFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Button button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        databaseFunctionality = new DatabaseFunctionalityClass(getApplicationContext());
        databaseFunctionality.startDB();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                startSearchProcess(editText.getText().toString());
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchProcess(editText.getText().toString());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(SearchPage_Activity.this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(SearchPage_Activity.this, ScanQrCodeActivity.class);
                    startActivityForResult(intent, 0);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Log.d("sent text:", data.getStringExtra("qr_code_text"));
                startSearchProcess(data.getStringExtra("qr_code_text"));
            }
        }
    }

    private void startSearchProcess(String query) {
        if (databaseFunctionality.checkIfDataExists(query)) {
            ItemData result = performSearch(query);
            goToNextPage(result);
        } else
            Toast.makeText(getApplicationContext(), "The \"" + query + "\" was not found in the database", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        editText.setText("");
    }

    private ItemData performSearch(String query) {
        if (databaseFunctionality.checkIfDataExists(query)) {
            ItemData item = databaseFunctionality.load(query);
            return item;
        } else {
            return null;
        }
    }

    private void goToNextPage(ItemData itemData) {
        Intent intent = new Intent();
        intent.putExtra("flowerData", itemData);
        intent.setClass(SearchPage_Activity.this, ResultPage_Activity.class);
        startActivity(intent);
    }
}
