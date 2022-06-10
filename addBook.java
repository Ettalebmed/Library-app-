package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

public class addBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText title_input, author_input, pages_input,quantity_input,description_input;
    ImageButton imgbutton;
    Button add_button;
    byte[] image = null;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        Spinner spinee = findViewById(R.id.type);
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this,R.array.books_type,
                                                android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinee.setAdapter(Adapter);
      spinee.setOnItemSelectedListener(this);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.add).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.add).setBackgroundResource(R.drawable.good_night_img);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        quantity_input = findViewById(R.id.quantity_input);
        description_input = findViewById(R.id.description_input);
        String type = spinee.getSelectedItem().toString();
        ImageButton pickImag = (ImageButton) findViewById(R.id.pickImag);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(addBook.this);
            BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            image = getBytes(bitmap);


            myDB.addBook(
                    title_input.getText().toString().trim(),
                    author_input.getText().toString().trim(),
                    Integer.parseInt(pages_input.getText().toString().trim()),
                    Integer.parseInt(quantity_input.getText().toString().trim()),
                    type,
                    description_input.getText().toString().trim(),image
                    );


            Toast.makeText(addBook.this,"Added Succefly",Toast.LENGTH_LONG).show();

        });
        //image button
        imgbutton=findViewById(R.id.pickImag);
        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

    }
    //opening the Gallery
    public void OpenGallery(){
            Intent intentImg= new Intent(Intent.ACTION_GET_CONTENT);
            intentImg.setType("image/*");
    startActivityForResult(intentImg,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==100){
            Uri uri =data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                imgbutton.setImageBitmap(decodeStream);
            }catch (Exception ex){
                Log.e("ex",ex.getMessage());

            }


        }    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    //**********************************************

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String textToast = "Profile Selected is "+adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(),textToast,Toast.LENGTH_SHORT).show();
//
//
//    }
//
//
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
