package com.example.shopsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

public class SingleItemScreen extends AppCompatActivity {
    TextView share,track,name,number,city,items,offers,desc;
    ImageView image;

    String Sname,Snumber,Scity,Sitems,Soffers,Sdesc,Simage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_screen);
        share=findViewById(R.id.share);
        track=findViewById(R.id.location);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        desc=findViewById(R.id.desc);
        city=findViewById(R.id.city);
        items=findViewById(R.id.items);
        offers=findViewById(R.id.offersview);
        image=findViewById(R.id.imageView);


        Sname=getIntent().getStringExtra("name");
        Snumber=getIntent().getStringExtra("number");
        Scity=getIntent().getStringExtra("city");
        Sitems=getIntent().getStringExtra("items");
        Sdesc=getIntent().getStringExtra("desc");
        Soffers=getIntent().getStringExtra("offers");
        Simage=getIntent().getStringExtra("image");

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");

                intent.putExtra(Intent.EXTRA_TEXT,"Name: "+Sname+ "Number: "+Snumber +"City: "+Scity +"Description: "+Sdesc);

                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(SingleItemScreen.this, "Please install whatsapp first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Starting Whatsapp
                startActivity(intent);
            }
        });
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoCodeLocation locationAddress = new GeoCodeLocation();
                locationAddress.getAddressFromLocation(Scity, getApplicationContext(), new
                        GeoCoderHandler());

            }
        });
        name.setText("Name: "+Sname);
        number.setText("Number: "+Snumber);
        desc.setText("Description: "+Sdesc);
        city.setText("City: "+Scity);
        items.setText("Items: "+Sitems);
        offers.setText("Offers:  "+Soffers);
        Glide.with(this).load(Simage).into(image);
    }
    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String lat,lon,res;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    lat = bundle.getString("lat");
                    lon = bundle.getString("lon");
                    res =bundle.getString("result");
                    break;
                default:
                    lat = null;
                    lon=null;
                    res=null;
            }
           // number.setText(res);
            Toast.makeText(SingleItemScreen.this,lat+"@@"+lon,Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SingleItemScreen.this,MapsActivity.class);
            i.putExtra("lat",lat);
            i.putExtra("lon",lon);
            startActivity(i);
        }

    }
}