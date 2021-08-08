package com.example.personalwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Records extends AppCompatActivity {

    ListView listView;

    DatabaseReference objDatabaseReference;

    List<Upload> upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__records);

        listView = findViewById(R.id.listView);

        upload = new ArrayList<>();

        viewAllFiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Upload objUpload = upload.get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setType(Intent.ACTION_VIEW);

                intent.setData(Uri.parse(objUpload.getUrl()));
                startActivity(intent);

            }
        });


    }

    private void viewAllFiles() {

        objDatabaseReference = FirebaseDatabase.getInstance().getReference("PDFiles");

        objDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    Upload objUpload = postSnapshot.getValue(Upload.class);

                    upload.add(objUpload);
                }

                String[] uploads = new String[upload.size()];

                for (int index = 0; index < uploads.length; index += 1){

                    uploads[index] = upload.get(index).getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 , uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
