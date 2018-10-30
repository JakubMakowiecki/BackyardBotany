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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


public class SearchPage_Activity extends AppCompatActivity {
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        startDB();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSearchStarted(editText.getText().toString());
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchStarted(editText.getText().toString());
            }
        });
    }

    private void onSearchStarted(String query) {
        if (checkIfDataExists(query)) {
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
        if (checkIfDataExists(query)) {
            ItemData item = load(query);
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

    public void save(String name, String info, String tips, String filePath) {
        StringBuilder sb = new StringBuilder();
        String text;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(name.toLowerCase() + ".txt", MODE_PRIVATE);
            text = sb.append(name).append("\n").append(info).append("\n").append(tips).append("\n").append(name.toLowerCase()).toString();
            fos.write(text.getBytes());
            String absFilePath = getFilesDir() + "/" + name;

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

    public ItemData load(String fileName) {
        ItemData retValue = new ItemData("", "", "", "");
        FileInputStream fis = null;


        try {
            fis = openFileInput(parseStringQuery(fileName) + ".txt");

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

            if ((text = br.readLine()) != null)
                name = sbName.append(text).toString();
            else
                name = "";

            if ((text = br.readLine()) != null)
                info = sbInfo.append(text).toString();
            else
                info = "";

            if ((text = br.readLine()) != null)
                tips = sbTips.append(text).toString();
            else
                tips = "";

            if ((text = br.readLine()) != null)
                imgFilepath = sbimgFilepath.append(text).toString();
            else
                imgFilepath = "";


            retValue.name = name;
            retValue.info = info;
            retValue.tips = tips;
            retValue.imgFilepath = imgFilepath;


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

        return retValue;
    }

    private String parseStringQuery(String inputQuery) {
        String outputQuery = inputQuery.toLowerCase();
        outputQuery = outputQuery.replaceAll("\\s", "");
        return outputQuery;
    }

    public void create(String name, String info, String tips, String filePath) {
        //if (!Objects.equals(load(name).tips, ""))
        {

            save(name, info, tips, filePath);
        }
    }

    public boolean checkIfDataExists(String name) {
        boolean retVal = false;
        if (!Objects.equals(load(name).tips, "")) {

            retVal = true;
        }

        return retVal;
    }

    public void startDB() {

        create("testItem",
                "this is info",
                "Don't water", "");
        create("Orchids",
                "Orchids are non-woody perennial plants are generally terrestrial herbs (i.e., growing on other plants rather than rooted in soil). Those attached to other plants often are vine-like and have a spongy root covering that absorbs water from the surrounding air.",
                "Orchids require shallow planting. These plants prefer bright, indirect light. Orchids need ample water but should be allowed to dry out some between watering.", "");
        create("Rose",
                "Small to medium size tree. Stem lined with thorns, spines or prickles.",
                "When pruning roses make sure to sharpen your pruning shears first, you want a quick, clean cut. Growing garlic in your rose garden will help keep away unwanted pests. Don't forget climbing roses! They are a wonderful addition to the garden and can make a barren wall look great. Try to water roses in the early morning so that any excess water on the leaves and blooms will evaporate quickly. Roses are very susceptible to fungal diseases. Plant your roses in an easily accessible location. These are high maintenance plants that will require frequent attention.", "");
        create("Tulips",
                "Tulips produce two or three thick, bluish green leaves that are clustered at the base of the plant. The flower is usually solitary bell-shaped and has three petals and three sepals.",
                "Plant tulip bulbs in the fall, 6 to 8 weeks before a hard frost is expected. This is usually during September and October in the north, and October and November in the south. Set the bulb in the hole with the pointy end up. Cover with soil and press soil firmly. Water bulbs right after planting. Although they can't bear wet feet, bulbs need water to trigger growth.", "");
        create("Sunflower",
                "Perennial sunflower species are not as popular for gardens due to their tendency to spread rapidly and become invasive.",
                "Sunflowers grow best in locations with direct sunlight (6 to 8 hours per day); they prefer long, hot summers to flower well. Once the plant is established, water deeply though infrequently to encourage deep rooting.", "");

    }

}
