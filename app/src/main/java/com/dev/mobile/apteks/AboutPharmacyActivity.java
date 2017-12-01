package com.dev.mobile.apteks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dev.mobile.apteks.Tasks.LoadPharmacyInfoTask;


public class AboutPharmacyActivity extends AppCompatActivity {



    public void findme(View view) {
        TextView textView = (TextView) findViewById(R.id.pharmacyAddress);
        String pharmacyadress = (String) textView.getText();

      //  String pharmacyadress = getIntent().getExtras().getString("pharmacyadress");
        String geoURI = "geo:0,0?q=красноярск+"+pharmacyadress+" 4&z=20";
        Uri geo = Uri.parse(geoURI);
        Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
        startActivity(geoMap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_pharmacy);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String pharmacyName = getIntent().getExtras().getString("pharmacyName");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        if(pharmacyName != null) {
            actionBar.setTitle(pharmacyName);
        }

        actionBar.setDisplayHomeAsUpEnabled(true);

        String pharmacyHref = getIntent().getStringExtra("pharmacyHref");


        new LoadPharmacyInfoTask(findViewById(android.R.id.content), pharmacyHref).execute();


        //String pharmacyadress = getIntent().getExtras().getString("pharmacyadress");

       // String[] array=this.getResources().getStringArray(R.array.empty);

        // TODO OMG!!!!!!!


        /*
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0; i<array.length; i++) {
            stringBuilder.append(array[i]+"\n");
        }
        TextView textView;
        textView= (TextView) findViewById(R.id.conactdata);
        textView.setText(stringBuilder.toString());
        */


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
