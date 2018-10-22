package com.example.qbook.backyardbotany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SearchActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //save("ex2.txt", "asjdgbsakgbslkdglkarbjlakb");

        load("rose.txt");

        //mEditText = findViewById(R.id.edit_text);

    }
    public void save(String name, String text) {
        //String text = mEditText.getText().toString();
        //String text = "TESTSETSETSETSTSETSE";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(name, MODE_PRIVATE);
            fos.write(text.getBytes());

            String absFilePath = getFilesDir() + "/" + name;

            //mEditText.getText().clear();

            Toast.makeText( this,  "Saved to " + absFilePath, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(String fileName) {
        FileInputStream fis = null;

        String absFilePath = getFilesDir() + "/" + fileName;


        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            String name;
            StringBuilder sbName = new StringBuilder();
            String info;
            StringBuilder sbInfo = new StringBuilder();
            String tips;
            StringBuilder sbTips = new StringBuilder();
            String imgFilepath;
            StringBuilder sbimgFilepath = new StringBuilder();

            if((text = br.readLine()) != null)
                name = sbName.append(text).toString();
            else
                name = "";

            if((text = br.readLine()) != null)
                info = sbInfo.append(text).toString();
            else
                info = "";

            if((text = br.readLine()) != null)
                tips = sbTips.append(text).toString();
            else
                tips = "";

            if((text = br.readLine()) != null)
                imgFilepath = sbimgFilepath.append(text).toString();
            else
                imgFilepath = "";

            /*while ((text = br.readLine()) != null)
            {
                sb.append(text).append("\n");
            }*/

            //mEditText.setText(sb.toString());


            Toast.makeText( this,  "Loaded: " + info, Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

