package com.example.qbook.backyardbotany;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class SearchPage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page_);
    }
        public void skip(View view){
            Intent intent = new Intent();
            intent.setClass(SearchPage_Activity.this,ResultPage_Activity.class);
            startActivity(intent);
    }
}
