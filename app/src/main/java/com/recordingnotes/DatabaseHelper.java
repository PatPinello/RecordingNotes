package com.recordingnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
   public Context context;
    private static final String DATABASE_NAME = "RecordingNotes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_Notes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "note_title";
    private static final String COLUMN_TEXT = "text";

    DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db)
    {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " title, " +
                        COLUMN_TEXT + " text);";
        db.execSQL(query);
    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String title, String text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TEXT, text);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result==-1)
        {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Added!", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TEXT, text);

        long result = db.update(TABLE_NAME,cv,"_id=?", new String[]{row_id});
        if(result==-1)
        {
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();

        }
    }

    void deleteOneRow(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long r = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});
        if(r==-1)
        {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}