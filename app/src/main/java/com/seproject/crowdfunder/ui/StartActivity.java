package com.seproject.crowdfunder.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.seproject.crowdfunder.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
    }

    public void LoginClicked(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void RegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void loginWithgoogle(View view) {
        Intent mailClient = new Intent(Intent.ACTION_VIEW);
        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
        startActivity(mailClient);
    }

    public void facebookLogin(View view) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }


    public static String FACEBOOK_URL = "https://www.facebook.com";
    //public static String FACEBOOK_PAGE_ID = "YourPageName";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" ;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
