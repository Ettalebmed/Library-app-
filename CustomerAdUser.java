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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CustomerAdUser extends RecyclerView.Adapter<CustomerAdUser.MyViewHolder> {

        private Context context;
        private Activity activity;
        EditText searchinput;


        private ArrayList book_id, book_title, book_author, book_pages,book_quantity,book_type,book_description;
        private ArrayList<byte[]> book_cover;


        CustomerAdUser(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                       ArrayList book_pages, ArrayList book_quantity, ArrayList book_type, ArrayList book_description,ArrayList book_cover){
            this.activity = activity;
            this.context = context;

            this.book_title = book_title;
            this.book_author = book_author;

            this.book_id = book_id;
            this.book_cover = book_cover;
            this.book_pages = book_pages;
            this.book_quantity = book_quantity;
            this.book_type = book_type;
            this.book_description = book_description;




        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.rowuser, parent, false);
            return new MyViewHolder(view);
        }

    @Override


    @RequiresApi(api = Build.VERSION_CODES.M)

        public void onBindViewHolder(@NonNull final com.example.afinal.CustomerAdUser.MyViewHolder holder, final int position) {
             Bitmap bitmap = BitmapFactory.decodeByteArray(book_cover.get(position), 0,book_cover.get(position).length);
            holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
            holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
//            holder.book_pages_txt.setText(String.valueOf(book_pages.get(position))+" p");
//            holder.book_quantity_txt.setText("Quantity :"+String.valueOf(book_quantity.get(position)));
//            holder.book_type_txt.setText(String.valueOf(book_type.get(position)));
//            holder.book_description_txt.setText(String.valueOf(book_description.get(position)));
            holder.book_cover_img.setImageBitmap(bitmap);
//        holder.book_cover_img.setImageBitmap(bitmap);
//        Recyclerview onClickListener
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {


                public void onClick(View view) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent intent = new Intent(context, book_details.class);
                    intent.putExtra("id", String.valueOf(book_id.get(position)));
                    intent.putExtra("title", String.valueOf(book_title.get(position)));
                    intent.putExtra("author", String.valueOf(book_author.get(position)));
                    intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                    intent.putExtra("quantity", String.valueOf(book_quantity.get(position)));
                    intent.putExtra("type", String.valueOf(book_type.get(position)));
                    intent.putExtra("description", String.valueOf(book_description.get(position)));
                    intent.putExtra("image",byteArray);

                    activity.startActivityForResult(intent, 2);
                }
            });


        }

        @Override
        public int getItemCount() {
            return book_author.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView book_cover_img;
            TextView book_title_txt, book_author_txt, book_pages_txt,book_quantity_txt,book_type_txt,book_description_txt;
            LinearLayout mainLayout;
            EditText searchinput;


            MyViewHolder(@NonNull View itemView) {
                super(itemView);

                book_title_txt = itemView.findViewById(R.id.book_title_txt_user);
                book_author_txt = itemView.findViewById(R.id.book_author_txt_user);
//                book_pages_txt = itemView.findViewById(R.id.book_pages_txt_user);
//                book_quantity_txt = itemView.findViewById(R.id.book_quantity_txt_user);
//                book_type_txt = itemView.findViewById(R.id.book_type_txt_user);
//                book_description_txt = itemView.findViewById(R.id.book_description_txt_user);
                book_cover_img =itemView.findViewById(R.id.cover_book_user);
                mainLayout = itemView.findViewById(R.id.mainLayout_user);
                searchinput=itemView.findViewById(R.id.searchinput);
                //Animate Recyclerview
                Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
                mainLayout.setAnimation(translate_anim);
            }

        }
//    public void filter(CharSequence charSequence){
//        ArrayList<String> temp = new ArrayList<>();
//        if(searchinput.getText().toString().isEmpty()){
//
//        }
//
//    }

    }


