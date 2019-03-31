package com.seproject.crowdfunder.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seproject.crowdfunder.PrivateRequest;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.FileListAdapter;
import com.seproject.crowdfunder.models.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DocumentUpload extends AppCompatActivity {

    private static final String TAG = "Document Upload";
    private Button btnChoose, btnUpload, btnContinue;
    private ImageView imageView;

//    private Uri filePath;
    public static FileListAdapter adapter;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    static public int flag = 0;
    public static ArrayList<File> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        files = new ArrayList<>();

        ListView listView = findViewById(R.id.documents);
        adapter = new FileListAdapter(files,this);
        listView.setAdapter(adapter);



        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        btnContinue = findViewById(R.id.btnContinue);
        //imageView = (ImageView) findViewById(R.id.imgView);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            File newFile = new File();
            newFile.setPath(data.getData());
            newFile.setFilename(data.getDataString());
            files.add(newFile);
            adapter.notifyDataSetChanged();
            btnUpload.setEnabled(true);

            //filePath = data.getData();

//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
        }
    }

    private void uploadImage() {

        for (File file : files ){
            if(file.getPath() != null)
            {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//
//                    }
//                }, 3000);

                progressDialog.create();
                StorageReference ref = storageReference.child("docs/"+ UUID.randomUUID().toString() + Math.random());
                ref.putFile(file.getPath())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(DocumentUpload.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                btnContinue.setEnabled(true);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DocumentUpload.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploading "+(int)progress+"%");
                            }
                        });
            }
        }

    }


    public void continueClicked(View view) {
        //Adding request to main
        databaseReference = FirebaseDatabase.getInstance().getReference(util.path_base_path);
        // Read from the database

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                util.no_of_requests = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(util.path_no_of_requests).getValue()).toString());
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(util.path_base_path);
                mRef.child(util.path_no_of_requests).setValue(util.no_of_requests + 1);


                util.request.setRequest_id(util.no_of_requests+1);
                mRef = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_requests + (util.no_of_requests + 1) + "/");
                mRef.setValue(util.request);



                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //finish();

        startActivity(new Intent(DocumentUpload.this, ChoosePublicPrivate.class));
        finish();
    }


    public void backClicked(View view) {
        super.onBackPressed();
    }
}
