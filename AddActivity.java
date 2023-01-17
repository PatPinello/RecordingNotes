package com.recordingnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText titleInput, textInput;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleInput = findViewById(R.id.titleInput);
        textInput = findViewById(R.id.textInput);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.addBook(
                        titleInput.getText().toString().trim(),
                        textInput.getText().toString().trim()
                        );
            }
        });



    }
}