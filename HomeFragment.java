package com.example.afinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    RecyclerView recyclerview_user;
    boolean clicked =false;
    ImageView empty_imageview;
    TextView no_data;
    EditText searchinput;
    byte[] image;
    ArrayList<byte[]> book_cover ;
    FloatingActionButton searchbutton;
    com.example.afinal.MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages,book_quantity,book_type,book_description;
    com.example.afinal.CustomerAdUser customAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragement_home, container, false);

        searchinput=view.findViewById(R.id.searchinput);
        recyclerview_user = view.findViewById(R.id.recyclerview_user);
        empty_imageview =view.findViewById(R.id.empty_imageviewuser);
        no_data = view.findViewById(R.id.no_datauser);
        myDB = new com.example.afinal.MyDatabaseHelper(getActivity());
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        book_quantity = new ArrayList<>();
        book_type = new ArrayList<>();
        book_description = new ArrayList<>();
        book_cover = new ArrayList<byte[]>();

        searchbutton = view.findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //clear data from the arraylist to stock in data returned from the search
               clicked=true;
               book_id.clear();
               book_title.clear();
               book_author.clear();
               book_pages.clear();
               book_quantity.clear();
               book_type.clear();
               book_description.clear();
               book_cover.clear();
               storeDataInArrays2();
               customAdapter.notifyDataSetChanged();

//
           }
       });

                storeDataInArrays();

            customAdapter = new com.example.afinal.CustomerAdUser(getActivity() ,getContext(),book_id, book_title, book_author, book_pages,book_quantity
                    ,book_type,book_description,book_cover);

        recyclerview_user.setAdapter(customAdapter);
        recyclerview_user.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            getActivity().recreate();

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
}}
        @SuppressLint("Range")
        void storeDataInArrays2(){
        {
         Cursor cursor = myDB.readAllSearchedData(searchinput.getText().toString());
//            Cursor cursor = myDB.readAllSearchedData("12");
            if(cursor.getCount() == 0 ){

                Toast.makeText(getContext(),"there is no Book with this title ",Toast.LENGTH_LONG).show();
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
            }


        }}





            }

