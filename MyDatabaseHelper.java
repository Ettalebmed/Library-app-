package com.example.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "book_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";
    private static final String COLUMN_QUANTITY = "book_quantity";
    private static final String COLUMN_TYPE = "book_type";
    private static final String COLUMN_DESCRIPTION = "book_description";
    private static final String COLUMN_LIKED = "book_liked";
    private static final String KEY_IMG = "image";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_PAGES + " INTEGER,"+
                        COLUMN_QUANTITY+" INTEGER,"+
                        COLUMN_TYPE+" TEXT,"+
                        COLUMN_DESCRIPTION+" TEXT,"+
                         KEY_IMG+" blob," +
                        COLUMN_LIKED+" Default \'NO\'," +
                        "reserved Default \'No\') ";


        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String title, String author, int pages,int quantity,String type,String description,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(KEY_IMG, image);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed...try again", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Book Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readAllSearchedData(String title){
        String query = "SELECT * FROM " + TABLE_NAME+" where book_title='"+title+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readFavoriteData(){
        String query = "SELECT * FROM " + TABLE_NAME+" where book_liked='YES'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readRervedData(){
        String query = "SELECT * FROM " + TABLE_NAME+" where book_liked='YES'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    //converting image from

    void updateData(String row_id, String title, String author, int pages,
                    int quantity,String type,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, "book_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "book_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    void add_to_favorite(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL("UPDATE "+TABLE_NAME +" Set book_liked=\'YES\' where book_id=?",new String[]{row_id});

            Toast.makeText(context, "Successfully added to favorite.", Toast.LENGTH_SHORT).show();
        }

    void reserve(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME +" Set book_quantity = book_quantity - 1 where book_id=?",new String[]{row_id});

        Toast.makeText(context, "Successfully reserveed ", Toast.LENGTH_SHORT).show();
    }


    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
