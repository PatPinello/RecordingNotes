package com.recordingnotes;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class UpdateActivity extends AppCompatActivity {

    EditText titleIn,textIn;
    Button updateButton, deleteButton;
    String id,title,text;
    private static final int micPerm =200;
    MediaRecorder mR;
    MediaPlayer mP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        if(isMic())
            getMicPerm();

        titleIn = findViewById(R.id.titleInput2);
        textIn = findViewById(R.id.textInput2);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.deleteButton);
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null)
        {
            ab.setTitle(title);
        }
        updateButton.setOnClickListener((view) -> {

                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                title=titleIn.getText().toString().trim();
                text=textIn.getText().toString().trim();
                myDB.updateData(id,title,text);
                finish();

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData()
    {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("text"))
        {
            //GET
            id = getIntent().getStringExtra("id");
            title =  getIntent().getStringExtra("title");
            text =  getIntent().getStringExtra("text");

            //SET
            titleIn.setText(title);
            textIn.setText(text);
        }
        else
        {
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void recordPressed(View v)
    {
        try
        {
            mR = new MediaRecorder();
            mR.setAudioSource(MediaRecorder.AudioSource.MIC);
            mR.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mR.setOutputFile(getPath());
            mR.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mR.prepare();
            mR.start();

            Toast.makeText(this,"Recording...", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void stopPressed(View v)
    {
        if(mR!=null)
        {
            mR.stop();
            mR.release();
            mR = null;
            Toast.makeText(this,"Recording stopped.", Toast.LENGTH_LONG).show();
        }

    }
    public void playPressed(View v)
    {
        try
        {
            mP = new MediaPlayer();
            mP.setDataSource(getPath());
            mP.prepare();
            mP.start();
            Toast.makeText(this,"Playing...", Toast.LENGTH_LONG).show();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    private boolean isMic()
    {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }
    private void getMicPerm()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO},micPerm);
        }
    }

    private String getPath()
    {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "recordingNotes" + ".mp3");
        return file.getPath();
    }

}