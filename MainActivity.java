package com.recordingnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    DatabaseHelper myDB;
    ArrayList<String> noteId,noteTitle,noteText;
    CustomAdapter cA;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        noteId = new ArrayList<>();
        noteTitle = new ArrayList<>();
        noteText = new ArrayList<>();

        storeDataInArrays();
        cA = new CustomAdapter(MainActivity.this,this,noteId,noteTitle,noteText);
        recyclerView.setAdapter(cA);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1)
        {
            recreate();
        }
    }
    void storeDataInArrays()
    {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() ==0)
        {
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                noteId.add(cursor.getString(0));
                noteTitle.add(cursor.getString(1));
                noteText.add(cursor.getString(2));
            }
        }
    }


}