package com.dev.mobile.apteks.Tasks;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.dev.mobile.apteks.Adapters.FindDrugsAdapter;
import com.dev.mobile.apteks.data.drugs.models.Drug;
import com.dev.mobile.apteks.R;

import java.util.ArrayList;


public class FindDrugTask  extends AsyncTask <Void, Void, ArrayList<Drug>>{
    private String searchString;
    private View view;
    private FindDrugsAdapter adapter;

    public FindDrugTask(View v, FindDrugsAdapter ad, String input) {
        this.searchString = input;
        this.view = v;
        this.adapter = ad;
    }

    @Override
    protected ArrayList<Drug> doInBackground(Void... params) {
        try {
            Spinner spinner = (Spinner) this.view.findViewById(R.id.sortSpinner);
            String sort = this.view.getResources().getStringArray(R.array.sortListValues)[spinner.getSelectedItemPosition()];

            spinner = (Spinner) this.view.findViewById(R.id.districtSpinner);
            String district = this.view.getResources().getStringArray(R.array.districtListValues)[spinner.getSelectedItemPosition()];

            return Drug.findDrug(this.searchString, sort, district, this.adapter);
        } catch(Exception ex) {
            Log.i("tag", "tag");
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        view.findViewById(R.id.findDrugProgressBar).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty).setVisibility(View.GONE);

        this.adapter.clear();

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Drug> data) {
        super.onPostExecute(data);

        view.findViewById(R.id.findDrugProgressBar).setVisibility(View.GONE);

        this.adapter.updateContent(data);


        if(this.adapter.isRealEmpty()) {
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.empty).setVisibility(View.GONE);
        }

    }
}
