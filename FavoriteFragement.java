package com.example.afinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


public class FavoriteFragement extends Fragment {
    RecyclerView recyclerview_favorite;
    boolean clicked =false;
    ImageView empty_imageview;
    TextView no_data;
    EditText searchinput;
    byte[] image;

    ArrayList<byte[]> book_cover ;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages,book_quantity,book_type,book_description;
    com.example.afinal.CustomerAdUser customAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view =inflater.inflate(R.layout.fragement_favorite, container, false);

        myDB =new MyDatabaseHelper(view.getContext());
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        book_quantity = new ArrayList<>();
        book_type = new ArrayList<>();
        book_description = new ArrayList<>();
        book_cover = new ArrayList<byte[]>();
        storeDataInArrays();
        customAdapter = new com.example.afinal.CustomerAdUser(getActivity() ,getContext(),book_id, book_title, book_author, book_pages,book_quantity
                ,book_type,book_description,book_cover);
        recyclerview_favorite = view.findViewById(R.id.favrecycler);
        recyclerview_favorite.setAdapter(customAdapter);
        recyclerview_favorite.setLayoutManager(new LinearLayoutManager(getActivity()));






        return view;

    }
    @SuppressLint("Range")
    void storeDataInArrays(){

        Cursor cursor = myDB.readFavoriteData();
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
}}}

