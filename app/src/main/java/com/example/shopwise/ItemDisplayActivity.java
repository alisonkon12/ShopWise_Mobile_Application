package com.example.shopwise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ItemDisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView title , description,shop;
    private Button share , map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        Bundle bundle = getIntent().getExtras();
        final String shopName = bundle.getString("shopName");
        String desc = bundle.getString("desc");
        String image = bundle.getString("imageUrl");
        String ID = bundle.getString("shopID");
        String dealTitle = bundle.getString("dealTitle");


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + dealTitle + " </font>"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imageView= (ImageView) findViewById(R.id.shopImage);
        Picasso.with(getApplicationContext()).load(image).into(imageView);
        title = (TextView) findViewById(R.id.title);
        title.setText(dealTitle);
        description=(TextView) findViewById(R.id.description);
        description.setText(desc);
        shop=(TextView) findViewById(R.id.shopName);
        shop.setText("Offered By : "+shopName);

        map=(Button) findViewById(R.id.btn_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("shopName", shopName);
                view.getContext().startActivity(intent);
            }
        });


        share = (Button) findViewById(R.id.btn_share);
        share.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = getIntent().getExtras();
        final String shopName = bundle.getString("shopName");
        String desc = bundle.getString("desc");
        String image = bundle.getString("imageUrl");

        Uri uri = Uri.parse(image);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shopName+" is offering now. Come Join Us");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shopName+" is offering now. Come Join Us!"+"\n"+desc);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share Deal"));
    }
}
