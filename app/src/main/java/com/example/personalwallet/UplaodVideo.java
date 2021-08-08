package com.example.personalwallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UplaodVideo extends AppCompatActivity {

    private EditText txtFileName;
    private Button btnUpload;

    private FirebaseDatabase objFirebaseDatabase;
    private FirebaseStorage objFirebaseStorage;
    private DatabaseReference objDatabaseReference;
    private StorageReference objStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_video);

        txtFileName = findViewById(R.id.txtFileName);
        btnUpload = findViewById(R.id.btnUpload);

        objFirebaseDatabase = FirebaseDatabase.getInstance();
        objFirebaseStorage = FirebaseStorage.getInstance();

        objDatabaseReference = FirebaseDatabase.getInstance().getReference("Videos");
        objStorageReference = FirebaseStorage.getInstance().getReference();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectVideos();

            }
        });


    }

    private void selectVideos() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10 && resultCode == RESULT_OK && data != null && data.getData() != null){

            uploadVideo(data.getData());
        }
    }

    private void uploadVideo(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();

        StorageReference reference = objStorageReference.child("video/"+System.currentTimeMillis()+".video");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());

                        Uri url = uri.getResult();

                        Upload objUploadVideo = new Upload(txtFileName.getText().toString(),url.toString());

                        objDatabaseReference.child(objDatabaseReference.push().getKey()).setValue(objUploadVideo);

                        Toast.makeText(UplaodVideo.this, "Video Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " +(int)progress+"%");

            }
        });


    }

    public void btn_action(View view) {

        startActivity(new Intent(getApplicationContext(),View_Videos.class));
    }
}
