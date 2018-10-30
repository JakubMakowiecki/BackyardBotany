package com.example.qbook.backyardbotany;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class SearchPage_Activity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private DatabaseFunctionalityClass databaseFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        button = findViewById(R.id.button);
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
