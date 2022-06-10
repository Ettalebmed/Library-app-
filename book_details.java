package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class book_details extends AppCompatActivity {
TextView title,author,pages,quantity,description;
ImageView cover;
String iddata, titledata, authordata,pagesdata,quantitydata,descriptiondata;
Bitmap coverdata;
Button favorite_button,reserve_button;
MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        //backgroundImage
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.details).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.details).setBackgroundResource(R.drawable.good_night_img);
        //************
        cover = findViewById(R.id.cover);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        quantity = findViewById(R.id.quantity);
        description = findViewById(R.id.description);
        pages = findViewById(R.id.pages);



        //getting data from intent
        iddata = getIntent().getStringExtra("id");
        titledata = getIntent().getStringExtra("title");
        authordata = getIntent().getStringExtra("author");
        pagesdata = getIntent().getStringExtra("pages");
        quantitydata = getIntent().getStringExtra("quantity");
        descriptiondata = getIntent().getStringExtra("description");
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //********************


        //set data in TextView and cover image
        cover.setImageBitmap(coverdata);
        title.setText(titledata);
        author.setText(authordata);
        pages.setText(pagesdata+" pages");
        quantity.setText("Quantity Available "+quantitydata);
        description.setText(descriptiondata);
        cover.setImageBitmap(bitmap);

        //
        reserve_button=findViewById(R.id.reserve_button);
        favorite_button=findViewById(R.id.favorite_button);
        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    db = new MyDatabaseHelper(book_details.this);
                    db.add_to_favorite(iddata);


            }
        });
        reserve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(quantitydata)<1){
                    Toast.makeText(book_details.this,"We are Sorry the book selected doesn't available Try again in the nex few day",Toast.LENGTH_LONG).show();
                }
                else{
                db=new MyDatabaseHelper(book_details.this);
                db.reserve(iddata);}
            }
        });

    }
}