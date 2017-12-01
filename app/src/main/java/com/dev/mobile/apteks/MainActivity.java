package com.dev.mobile.apteks;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dev.mobile.apteks.presentation.search.FindDrugsActivity;


public class MainActivity extends AppCompatActivity {

    public static final String TAG="MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(TAG, "onCreate()");
        Log.d(TAG,"onCreate()");
        Log.d(TAG,"onCreate()");
    }


    public void findDrugs(View view) {
        Intent intent = new Intent(this, FindDrugsActivity.class);
        startActivity(intent);
    }


    public void findaptek(View view) {
        TextView textView;
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Version 1");
        String geoURI = "geo:0,0?q=красноярск+аптеки&z=20";
        Uri geo = Uri.parse(geoURI);
        Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
        startActivity(geoMap);

    }


}
