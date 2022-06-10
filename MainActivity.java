package com.example.afinal;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Animation rotateOpen,rotatClose,fromBottom,toBottom;


    RecyclerView recyclerView;
    FloatingActionButton add_book,addadmin,adduser,add_button;
    ImageView empty_imageview;
    TextView no_data;
    byte[] image;
    ArrayList<byte[]> book_cover ;

    com.example.afinal.MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages,book_quantity,book_type,book_description;
    com.example.afinal.CustomAdapter customAdapter;
    private boolean clicked=false;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        //change the backround according to time

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        if(timeOfDay >= 8 && timeOfDay < 19)
            findViewById(R.id.main).setBackgroundResource(R.drawable.good_morning_img);
        else findViewById(R.id.main).setBackgroundResource(R.drawable.good_night_img);
        //**********************************************
        //Displaying floating Button
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClick();
            }
        });



        //***********************************************
        //ANimation
          rotateOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.open_rotate);
          rotatClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.close_rotate);
          fromBottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_buttom_animation);;
          toBottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom);

        //***********************************************


        recyclerView = findViewById(R.id.recyclerView);
        add_book = findViewById(R.id.add_book);
        adduser = findViewById(R.id.add_user);

        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        //************************************
        add_book.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"add Book ",Toast.LENGTH_SHORT);
            Intent intent = new Intent(MainActivity.this,addBook.class);
            startActivity(intent);
        });
        //**********************************************
        addadmin=findViewById(R.id.add_admin);
        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"add an Admin ",Toast.LENGTH_SHORT);
                Intent intent2 = new Intent(MainActivity.this,addAdmin.class);
                startActivity(intent2);
            }
        });
        //**********************************************
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"add a User ",Toast.LENGTH_SHORT);
                Intent intent2 = new Intent(MainActivity.this,adduser.class);
                startActivity(intent2);
            }
        });

            //**********************************************


        myDB = new com.example.afinal.MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        book_quantity = new ArrayList<>();
        book_type = new ArrayList<>();
        book_description = new ArrayList<>();
        book_cover = new ArrayList<byte[]>();
        byte[] image ;

        storeDataInArrays();

        customAdapter = new com.example.afinal.CustomAdapter(MainActivity.this,this, book_id, book_title, book_author,
                book_pages,book_quantity,book_type,book_description,book_cover);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    private void onAddButtonClick() {
        setVisibility(clicked);
        setAnimation(clicked);
        if(!clicked) clicked=true ; else clicked=false;
    }

    private void setAnimation(boolean clicked) {
        if(!clicked){
            add_button.startAnimation(rotateOpen);
            adduser.startAnimation(fromBottom);
            addadmin.startAnimation(fromBottom);
            add_book.startAnimation(fromBottom);

        }
        else{
            add_button.startAnimation(rotatClose);
            adduser.startAnimation(toBottom);
            addadmin.startAnimation(toBottom);
            add_book.startAnimation(toBottom);



        }
    }
    private void setVisibility(boolean clicked) {
        if(!clicked){
            adduser.setVisibility(View.VISIBLE);
            addadmin.setVisibility(View.VISIBLE);
            add_book.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    @SuppressLint("Range")
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
                book_quantity.add(cursor.getString(4));
                book_type.add(cursor.getString(5));
                book_description.add(cursor.getString(6));
                image=cursor.getBlob(cursor.getColumnIndex("image"));
                book_cover.add(image);


            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
            myDB.deleteAllData();
            //Refresh Activity
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        builder.create().show();
    }
}
