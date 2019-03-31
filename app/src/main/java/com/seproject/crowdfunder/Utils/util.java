package com.seproject.crowdfunder.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.seproject.crowdfunder.models.DistanceRequest;
import com.seproject.crowdfunder.models.Request;
import com.seproject.crowdfunder.models.RequestShortDetails;
import com.seproject.crowdfunder.models.User;

import java.util.ArrayList;
import java.util.List;


public class util {

    static public String SHARED_PREFERNCES_USER_DETAILS = "User Details";
    static public String SHARED_PREFERNCES_USER_DETAILS_EMAIL = "email";
    static public String SHARED_PREFERNCES_USER_DETAILS_NAME = "name";
    static public String SHARED_PREFERNCES_USER_DETAILS_PROFILE_PIC_PATH = "profile";
    static public String SHARED_PREFERNCES_USER_DETAILS_RATING = "rating";
    static public String SHARED_PREFERNCES_USER_DETAILS_UID = "000";



    //firebase
    static public String path_base_path = "sub/";
    static public String path_payment_history = "payment_history/";
    static public String path_request_history = "request_history/";
    static public String path_bookmarks = "bookmarks/";
    static public String path_no_of_bookmarks = "no_of_bookmarks/";
    static public String path_user = "user/";
    static public String path_no_of_requests = "no_of_requests";
    static public String path_requests = "request/";
    static public String path_donations = "donations/";
    static public String path_profile_pic = "profile/";
    static public String path_docs = "docs/";
    static public String path_views = "views";



    //type
    static public int STRING = 0;
    static public int INT = 1;
    static public int FLOAT = 2;

    //
    static public int LOGOUT = 2;
    public static final int BACK = 1;


    public static String email;
    static public User user = new User();
    static public Request request = new Request();
    static public RequestShortDetails requestShortDetails = new RequestShortDetails();
    public static int no_of_requests;



    static public ArrayList<DistanceRequest> distanceRequestArrayList = new ArrayList<>();
    static public List<Request> requestsNearYou = new ArrayList<>();
//    static public RequestShortDetails requestShortDetails = new RequestShortDetails();


    static public void writeIntoSharedPref(Context context, String sharedPrefFilename, String key, String value, int mode){
        SharedPreferences pref = context.getSharedPreferences(sharedPrefFilename, mode); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static public void writeIntoSharedPref(Context context, String sharedPrefFilename, String key, float value, int mode){
        SharedPreferences pref = context.getSharedPreferences(sharedPrefFilename, mode); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    static public void showMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    static public String readFromSharedPreferencesString(Context context, String sharedPrefName,String key, int mode ){
              SharedPreferences pref = context.getSharedPreferences(sharedPrefName, mode); // 0 - for private mode
              return pref.getString(key, "");
    }

    static public Float readFromSharedPreferencesFloat(Context context, String sharedPrefName,String key, int mode ){
        SharedPreferences pref = context.getSharedPreferences(sharedPrefName, mode); // 0 - for private mode
        return pref.getFloat(key, (float)0.00);
    }


    static public double kilometerDistanceBetweenPoints(Double lat_a, Double lng_a, Double lat_b, Double lng_b) {
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
