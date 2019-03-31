package com.seproject.crowdfunder.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class ProfileActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setDetails();

    }

    private void setDetails() {
        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.mail_id);
        RatingBar rating = findViewById(R.id.rating);
        profileImage = findViewById(R.id.profilePic);


        email.setText(util.readFromSharedPreferencesString(ProfileActivity.this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_EMAIL,0));
        name.setText(util.readFromSharedPreferencesString(ProfileActivity.this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_NAME,0));
        rating.setRating(util.readFromSharedPreferencesFloat(ProfileActivity.this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_RATING,0));

        getProfileImage();

    }

    private void getProfileImage() {
        StorageReference islandRef = storageReference.child( "profile/"+util.user.getUid()+ ".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profileImage.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(ProfileActivity.this, "no profile pic",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void closeClicked(View view) {
        setResult(util.BACK);
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(util.BACK);
        super.onBackPressed();
    }


    public void EditProfileClicked(View view) {
            // custom dialog
            final Dialog dialog = new Dialog(ProfileActivity.this);
            dialog.setContentView(R.layout.layout_dialog_profile);
            dialog.setTitle("Update Name...");

            // set the custom dialog components - text, image and button


            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            // if button is clicked, close the custom dialog
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            final EditText name = dialog.findViewById(R.id.name_input);
            Button ok = (Button) dialog.findViewById(R.id.ok);
            // if button is clicked, close the custom dialog
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (name.getText().toString().length() < 8)
                        Toast.makeText(ProfileActivity.this, "Name too short",Toast.LENGTH_SHORT).show();
                    else
                    {
                        String nam = name.getText().toString();
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(util.path_base_path + util.path_user + util.user.getUid());
                        myRef.child("name").setValue(name.getText().toString());
                        util.writeIntoSharedPref(ProfileActivity.this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_NAME,name.getText().toString(),0);
                        TextView textView  = findViewById(R.id.name);
                        textView.setText(nam);
                        dialog.dismiss();
                    }
                }
            });

        dialog.show();

    }

    public void editProfilePic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                ImageView image = findViewById(R.id.profilePic);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
                uploadProfileImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }

    private void uploadProfileImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child( "profile/" + util.user.getUid()+ ".jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void DeactivateAccountClicked(View view) {
        startActivity(new Intent(this, ConfirmingPassword.class));
    }

    public void requestHist(View view) {
        startActivity(new Intent(ProfileActivity.this, RequestHistoryActivity.class));
    }

    public void yourBookmarkClicked(View view) {
        startActivity(new Intent(this, BookmarksActivity.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        setResult(util.LOGOUT);
        finish();
    }
}
