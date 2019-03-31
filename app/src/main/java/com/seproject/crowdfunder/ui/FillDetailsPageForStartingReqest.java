package com.seproject.crowdfunder.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.Utils.GPSTracker;
import com.seproject.crowdfunder.Utils.util;

import java.util.Calendar;

public class FillDetailsPageForStartingReqest extends AppCompatActivity {
    int flag = 0;
    EditText title, desc, amount_required,days;
    TextView date;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    GPSTracker gpsTracker;
    Double lat,longi;
    Location yourLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details_page_for_starting_reqest);

        while(true)
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            }
            else{
                gpsTracker = new GPSTracker(this);
                yourLocation = new Location("A");
                yourLocation.setLatitude(gpsTracker.getLatitude());
                yourLocation.setLongitude(gpsTracker.getLongitude());
                break;
            }




        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        amount_required = findViewById(R.id.amount_required);
        date = findViewById(R.id.date);
        days = findViewById(R.id.days_left);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);







    }

    public void gotocheck(View view) {
        flag = 0;
        if(title.getText().toString().length() <= 4)
            flag = 1;
        else if(desc.getText().toString().length() <= 4)
            flag = 2;
        else if(amount_required.getText().toString().length() <= 2)
            flag = 3;
        else if(date.getText().toString().length() <= 2)
            flag = 4;

        switch (flag){
            case 0:
                util.request.setTitle(title.getText().toString());
                util.request.setDesc(desc.getText().toString());
                util.request.setAmount_required(Float.parseFloat(amount_required.getText().toString()));
                util.request.setStart_date(day+"/"+month+"/"+year);
                util.request.setLon((float)yourLocation.getLongitude());
                util.request.setLat((float)yourLocation.getLatitude());
                util.request.setUid(util.user.getUid());
                util.request.setViews(0);
                util.request.setDays_left(Integer.parseInt(days.getText().toString()));
                util.request.setUser_name(util.user.getName());
                startActivity(new Intent(this, DocumentUpload.class));
                finish();
                break;
            case 1: util.showMessage(this, "Specify title");
                break;
            case 2:util.showMessage(this, "Specify description");
                break;

            case 3:util.showMessage(this, "Specify Amount");
                break;

            case 4:util.showMessage(this, "Specify Date");
                break;
        }

    }

    public void gotToDocumentUpload(View view) {

    }



    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }



    public void showDistance(View view) {
        TextView textView = findViewById(R.id.distance);
        Double distance = kilometerDistanceBetweenPoints(yourLocation.getLatitude(),yourLocation.getLongitude(),lat,longi);
        textView.setText(Double.toString(distance));
    }

    private double kilometerDistanceBetweenPoints(Double lat_a, Double lng_a, Double lat_b, Double lng_b) {
        Double pk = (Double) (180.f/Math.PI);

        Double a1 = lat_a / pk;
        Double a2 = lng_a / pk;
        Double b1 = lat_b / pk;
        Double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366 * tt;
    }


}
