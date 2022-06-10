package com.example.afinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;


    private ArrayList book_id, book_title, book_author, book_pages,book_quantity,book_type,book_description;
    private ArrayList<byte[]> book_cover;


    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                  ArrayList book_pages, ArrayList book_quantity, ArrayList book_type, ArrayList book_description,ArrayList book_cover){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.book_quantity = book_quantity;
        this.book_type = book_type;
        this.book_description = book_description;
        this.book_cover = book_cover;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,final int position) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(book_cover.get(position), 0,book_cover.get(position).length);
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position))+" p");
        holder.book_quantity_txt.setText("Quantity :"+String.valueOf(book_quantity.get(position)));
        holder.book_type_txt.setText(String.valueOf(book_type.get(position)));
        holder.book_description_txt.setText(String.valueOf(book_description.get(position)));
        holder.book_cover_img.setImageBitmap(bitmap);
//        holder.book_cover_img.setImageBitmap(bitmap);
//        Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                intent.putExtra("quantity", String.valueOf(book_quantity.get(position)));
                intent.putExtra("type", String.valueOf(book_type.get(position)));
                intent.putExtra("description", String.valueOf(book_description.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView book_cover_img;
        TextView  book_title_txt, book_author_txt, book_pages_txt,book_quantity_txt,book_type_txt,book_description_txt;
        LinearLayout mainLayout;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            book_quantity_txt = itemView.findViewById(R.id.book_quantity_txt);
            book_type_txt = itemView.findViewById(R.id.book_type_txt);
            book_description_txt = itemView.findViewById(R.id.book_description_txt);
            book_cover_img =itemView.findViewById(R.id.cover_book);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
