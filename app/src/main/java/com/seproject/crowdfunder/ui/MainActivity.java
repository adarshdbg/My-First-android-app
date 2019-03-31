package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.seproject.crowdfunder.BuildConfig;
import com.seproject.crowdfunder.Login;
import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.GPSTracker;
import com.seproject.crowdfunder.Utils.util;
import com.seproject.crowdfunder.adapter.ViewPagerAdapter;
import com.seproject.crowdfunder.models.DistanceRequest;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;
import com.seproject.crowdfunder.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private List<RequestShortDetails> requestShortDetails = new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;


    GPSTracker gpsTracker;
    Double lat,longi;
    public static Location yourLocation;
    FirebaseStorage storage;
    StorageReference storageReference;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        while(true) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            } else {
                gpsTracker = new GPSTracker(this);
                yourLocation = new Location("A");
                yourLocation.setLatitude(gpsTracker.getLatitude());
                yourLocation.setLongitude(gpsTracker.getLongitude());
                break;
            }
        }



        setUserDetails();
        FirebaseUser FUser = FirebaseAuth.getInstance().getCurrentUser();
        if (FUser != null) {
            util.user = new User();
            util.user.setUid(FUser.getUid());
            uploadUserDetails(util.user.getUid());
        }

        setContentView(R.layout.activity_home);
//        setNameandEmail();







        //get near requests near you
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(util.path_base_path + util.path_requests);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Double lat = Double.parseDouble(dataSnapshot1.child("lat").getValue().toString());
                    Double lon = Double.parseDouble(dataSnapshot1.child("lon").getValue().toString());
                    double dist = util.kilometerDistanceBetweenPoints(yourLocation.getLatitude(),yourLocation.getLongitude(),lat,lon);;
                    util.distanceRequestArrayList.add(new DistanceRequest(dataSnapshot1.child("request_id").getValue().toString(),dist) );
                }

                myRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        util.distanceRequestArrayList.sort(new Comparator<DistanceRequest>() {
            @Override
            public int compare(DistanceRequest o1, DistanceRequest o2) {
                if(o1.getDistance() == o2.getDistance())
                    return 0;
                else if(o1.getDistance() > o2.getDistance())
                    return 1;
                else
                    return -1;
            }
        });


        final DatabaseReference myRef1 = database.getReference(util.path_base_path + util.path_requests);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                for (DistanceRequest distanceRequest : util.distanceRequestArrayList){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        if(dataSnapshot1.child("request_id").toString().matches(distanceRequest.getRequest_id()))
                            util.requestsNearYou.add(dataSnapshot1.getValue(Request.class));
                    }

                }

                myRef.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        Toolbar toolbar =  findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start a request", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class) ,util.LOGOUT );
            }
        });

        //set name and email
        TextView name = headerView.findViewById(R.id.navName);
        TextView mail = headerView.findViewById(R.id.navMail);
        final ImageView proImage = headerView.findViewById(R.id.pro_image_nav);

        name.setText(util.readFromSharedPreferencesString(this, util.SHARED_PREFERNCES_USER_DETAILS,util.SHARED_PREFERNCES_USER_DETAILS_NAME,0));
        mail.setText(util.readFromSharedPreferencesString(this, util.SHARED_PREFERNCES_USER_DETAILS,util.SHARED_PREFERNCES_USER_DETAILS_EMAIL,0));



        StorageReference islandRef = storageReference.child( "profile/"+util.user.getUid()+ ".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                proImage.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity.this, "no profile pic",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setUserDetails() {
        util.user.setUid(util.readFromSharedPreferencesString(this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_UID,0));
        util.user.setName(util.readFromSharedPreferencesString(this, util.SHARED_PREFERNCES_USER_DETAILS, util.SHARED_PREFERNCES_USER_DETAILS_NAME,0));
    }




    public void uploadUserDetails(String uid){
        //upload profile data


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_bookmarked) {
            startActivity(new Intent(this, BookmarksActivity.class));

        }else if (id == R.id.nav_start_a_request) {
            startActivity(new Intent(this, StartARequestActivity.class));
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "App should be uploaded to playstore to share it",Toast.LENGTH_LONG).show();
            //share_the_app();

        } else if (id == R.id.nav_rate_us) {
            startActivity(new Intent(this, RateUsActivity.class));
        }else if (id == R.id.nav_contact_us) {

            startActivity(new Intent(this, ContactUs.class));
        }else if (id == R.id.nav_about) {

        }
        else if (id == R.id.nav_chat) {
            startActivity(new Intent(this, Login.class));
        }
        else if (id == R.id.nav_rate_user) {
            startActivity(new Intent(this, RatingTheUser.class));
        }
        else if (id == R.id.nav_explore) {
            startActivity(new Intent(this, ExploreActivity.class));
        }
        else if(id == R.id.nav_payment)
        {
            startActivity(new Intent(this, payment.class));
        }
        else if(id == R.id.nav_balance)
        {
            startActivity(new Intent(this, wallet.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.refresh, menu);
        return false;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // toggle nav drawer on selecting action bar app icon/title
////        if (dr.onOptionsItemSelected(item)) {
////            return true;
////        }
//        // Handle action bar actions click
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                Toast.makeText(MainActivity.this, "Refreshed",Toast.LENGTH_LONG).show();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
    private void share_the_app() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    public void prepareRequestList() {
        int i = 0;
        while (i < 10) {
            requestShortDetails.add(new RequestShortDetails());
            i++;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        switch (resultCode){
            case 1: // util.BACK
                break;
            case 2: //util.LOGOUT
                startActivity(new Intent(this, StartActivity.class));
                finish();
                break;
        }
    }

}
