package com.example.qbook.backyardbotany;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends AppCompatActivity {
    private MyDBHelper helper;
    private SQLiteDatabase mydb;
    private EditText edit1, edit2, edit3, edit4, edit5, edit6;
    private Button button1, button2, button3, button4;
    private TextView tv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.initDB();
        this.initViews();
        this.initListeners();
    }

    private void initDB() {
        helper = new MyDBHelper(SearchActivity.this);
        mydb = helper.getWritableDatabase();
    }

    private void initViews() {
        edit1 = (EditText) findViewById(R.id.edit01);
        edit2 = (EditText) findViewById(R.id.edit02);
        edit3 = (EditText) findViewById(R.id.edit03);
        edit4 = (EditText) findViewById(R.id.edit04);
        edit5 = (EditText) findViewById(R.id.edit05);
        edit6 = (EditText) findViewById(R.id.edit06);
        button1 = (Button) findViewById(R.id.button01);
        button2 = (Button) findViewById(R.id.button02);
        button3 = (Button) findViewById(R.id.button03);
        button4 = (Button) findViewById(R.id.button04);
        tv10 = (TextView) findViewById(R.id.text10);
    }

    private void initListeners() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit1.getText().toString();
                String description = edit2.getText().toString();
                String tip = edit3.getText().toString();
                insertData(name, description, tip);
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                Toast.makeText(SearchActivity.this, "Add Data Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit4.getText().toString();
                String tip = edit5.getText().toString();
                updateData(name, tip);
                edit4.setText("");
                edit5.setText("");
                Toast.makeText(SearchActivity.this, "Modify Data Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit6.getText().toString();
                removeData(name);
                edit6.setText("");
                Toast.makeText(SearchActivity.this, "Delete Data Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });
    }

    private void insertData(String name, String description, String tip) {
        String sql = "INSERT INTO " + helper.TABLE_NAME + " (name,description,tip) VALUES ('" + name + "','" + description + "','" + tip + "')";
        mydb.execSQL(sql);
    }

    private void updateData(String name, String tip) {
        String sql = "UPDATE " + helper.TABLE_NAME + " SET tip='" + tip + "' WHERE name= '" + name + "'";
        mydb.execSQL(sql);
    }

    private void removeData(String name) {
        String sql = "DELETE FROM " + helper.TABLE_NAME + " WHERE name='" + name + "'";
        mydb.execSQL(sql);
    }

    private void queryData() {
        String sql = "SELECT * FROM " + helper.TABLE_NAME;
        Cursor cursor = mydb.rawQuery(sql, null);
        String result = null;
        if (cursor.getCount() == 0) {
            result = "No Data Now";
        } else {
            result = "";
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                String tip = cursor.getString(2);
                String temp = "Flower name：" + name + "\n" + "Description： " + description + "\n" + "Tip：" + tip + "\n";
                result += temp;
            }
        }
        tv10.setText(result);
    }
}
