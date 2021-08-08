package com.example.personalwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnPhoto, btnVideo, btnFile, btnAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPhoto = findViewById(R.id.btnPhotos);
        btnVideo = findViewById(R.id.btnVideo);
        btnFile = findViewById(R.id.btnFiles);
        btnAudio = findViewById(R.id.btnAudios);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),UploadPicture.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"You Click Photos",Toast.LENGTH_SHORT).show();
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),UplaodVideo.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"You Click Videos",Toast.LENGTH_SHORT).show();
            }
        });

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UploadPDF.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"You Click Files",Toast.LENGTH_SHORT).show();
            }
        });

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),UploadAudio.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"You Click Audios",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
