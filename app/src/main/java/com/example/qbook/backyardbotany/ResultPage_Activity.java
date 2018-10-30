package com.example.qbook.backyardbotany;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultPage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        Intent i = getIntent();
        ItemData itemData = (ItemData) i.getParcelableExtra("flowerData");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(itemData.name);

        TextView flowerDescription = findViewById(R.id.flower_description);
        TextView flowerTips = findViewById(R.id.flower_tips);
        ImageView flowerPicture = findViewById(R.id.flower_picture);


        flowerDescription.setText(itemData.info);
        flowerTips.setText(parseTips(itemData.tips));

        String drawableUri = "@drawable/";
        String flowerPictureUri = drawableUri + (itemData.imgFilepath);
        int imageResource = getResources().getIdentifier(flowerPictureUri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        flowerPicture.setImageDrawable(res);
    }

    private String parseTips(String input) {
        String parsedString = "• " + input.replaceAll("\\. ", ".\r\n• ");
        return parsedString;
    }
}
