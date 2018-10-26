package com.example.qbook.backyardbotany;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class SearchPage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);

        startDB();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String query = editText.getText().toString();
                performSearch(query);
                goToNextPage();
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();
                performSearch(query);
                goToNextPage();
            }
        });



    }

    private void performSearch(String query) {
        Log.d("searching for:", query);
        if (search(query))
        {

            itemData item = load(query);


            Log.d("Found: ", item.name);
            Log.d("Found info: ", item.info);
            Log.d("Found tips: ", item.tips);
            Log.d("Found imgFilepath: ", item.imgFilepath);

        }
        else
        {
            Log.d("Didn't find: ", query);

        }


    }

    private void goToNextPage() {
        Intent intent = new Intent();
        intent.setClass(SearchPage_Activity.this, ResultPage_Activity.class);
        startActivity(intent);
    }

    public void save(String name, String info, String tips) {
        //String text = mEditText.getText().toString();
        StringBuilder sb = new StringBuilder();
        String text;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(name + ".txt", MODE_PRIVATE);

            text = sb.append(name).append("\n").append(info).append("\n").append(tips).append("\n").append(name).append(".bmp").toString();
            fos.write(text.getBytes());

            String absFilePath = getFilesDir() + "/" + name;

            //mEditText.getText().clear();

            //Toast.makeText( this,  "Saved to " + absFilePath, Toast.LENGTH_LONG).show();

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

    public itemData load(String fileName) {
        itemData retValue = new itemData("","","","");
        FileInputStream fis = null;


        try {
            fis = openFileInput(fileName + ".txt");

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


            //mEditText.setText(sb.toString());


            //Toast.makeText( this,  "Loaded: " + name, Toast.LENGTH_LONG).show();

            retValue.name = name;
            retValue.info = info;
            retValue.tips = tips;
            retValue.imgFilepath = imgFilepath;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return retValue;
    }

    public void create(String name, String info, String tips)
    {
        if (!Objects.equals(load(name).tips, ""))
        {
            //Toast.makeText( this,  "creating " + name , Toast.LENGTH_LONG).show();

            save(name,info,tips);
        }
    }

    public boolean search(String name)
    {
        boolean retVal = false;

        if (!Objects.equals(load(name).tips, ""))
        {
            //Toast.makeText( this,  "Found: " + load(name).tips, Toast.LENGTH_LONG).show();

            retVal = true;
        }

        return retVal;
    }

    public void startDB()
    {

        create("testItem",
                "this is info",
                "dont water");
        create("Orchids",
                "Orchids are nonwoody perennial plants are generally terrestrial herbs (i.e., growing on other plants rather than rooted in soil). Those attached to other plants often are vine-like and have a spongy root covering that absorbs water from the surrounding air.",
                "Orchids require shallow planting. These plants prefer bright, indirect light. Orchids need ample water but should be allowed to dry out some between watering.");
        create("Rose",
                "Small to medium size tree. Stem lined with thorns, spines or prickles.",
                "When pruning roses make sure to sharpen your pruning shears first, you want a quick, clean cut. Growing garlic in your rose garden will help keep away unwanted pests. Don't forget climbing roses! They are a wonderful addition to the garden and can make a barren wall look great. Try to water roses in the early morning so that any excess water on the leaves and blooms will evaporate quickly. Roses are very susceptible to fungal diseases. Plant your roses in an easily accessible location. These are high maintenance plants that will require frequent attention.");
        create("Tulips",
                "Tulips produce two or three thick, bluish green leaves that are clustered at the base of the plant. The flower is usually solitary bell-shaped and has three petals and three sepals.",
                "Plant tulip bulbs in the fall, 6 to 8 weeks before a hard frost is expected. This is usually during September and October in the north, and October and November in the south. Set the bulb in the hole with the pointy end up. Cover with soil and press soil firmly. Water bulbs right after planting. Although they can't bear wet feet, bulbs need water to trigger growth.");
        create("Sunflower",
                "Perennial sunflower species are not as popular for gardens due to their tendency to spread rapidly and become invasive.",
                "Sunflowers grow best in locations with direct sunlight (6 to 8 hours per day); they prefer long, hot summers to flower well. Once the plant is established, water deeply though infrequently to encourage deep rooting.");

    }

}
