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
    private EditText name, stunum, specialty;
    private Button add, update, remove, show;
    private TextView showin;
    private MyDBHelper helper;
    private SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        name = (EditText) findViewById(R.id.name);
        stunum = (EditText) findViewById(R.id.stunum);
        specialty = (EditText) findViewById(R.id.specialty);
        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        remove = (Button) findViewById(R.id.remove);
        show = (Button) findViewById(R.id.show);
        showin = (TextView) findViewById(R.id.showin);
        helper = new MyDBHelper(SearchActivity.this);
        mydb = helper.getWritableDatabase();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stunum1 = Integer.parseInt(stunum.getText().toString());
                String name1 = name.getText().toString();
                String specialty1 = specialty.getText().toString();
                insertData(stunum1, name1, specialty1);
                name.setText("");
                stunum.setText("");
                specialty.setText("");
                Toast.makeText(SearchActivity.this, "插入数据成功", Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stunum1 = Integer.parseInt(stunum.getText().toString());
                String spcialty1 = specialty.getText().toString();
                updateData(stunum1, spcialty1);
                stunum.setText("");
                specialty.setText("");
                Toast.makeText(SearchActivity.this, "修改数据成功", Toast.LENGTH_SHORT).show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stunum1 = Integer.parseInt(stunum.getText().toString());
                removeData(stunum1);
                stunum.setText("");
                Toast.makeText(SearchActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });
    }

    private void insertData(int stunum, String name, String specialty) {
        String sql = "INSERT INTO " + "students" + " (stunum,name,specialty) VALUES (" + stunum + ",'" + name + "'," + specialty + ")";
        mydb.execSQL(sql);
    }

    private void updateData(int stunum, String specialty) {
        String sql = "UPDATE " + "students" + " SET specialty=" + specialty + " WHERE stunum=" + stunum;
        mydb.execSQL(sql);
    }

    private void removeData(int stunum) {
        String sql = "DELETE FROM " + "students" + " WHERE stunum=" + stunum;
        mydb.execSQL(sql);
    }

    private void queryData() {
        String sql = "SELECT * FROM " + "students";
        Cursor cursor = mydb.rawQuery(sql, null);
        String result = null;
        if (cursor.getCount() == 0) {
            result = "无数据";
        } else {
            result = "";
            while (cursor.moveToNext()) {
                int stunum = cursor.getInt(0);
                String name = cursor.getString(1);
                String specialty = cursor.getString(2);
                String temp = "学号：" + stunum + ",  姓名：" + name + ",  专业：" + specialty + "\n";
                result += temp;
            }
        }
        showin.setText(result);
    }
}