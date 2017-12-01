package com.dev.mobile.apteks.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Spinner;

import com.dev.mobile.apteks.Adapters.FindDrugsAdapter;
import com.dev.mobile.apteks.presentation.search.FindDrugsActivity;
import com.dev.mobile.apteks.data.drugs.models.Drug;
import com.dev.mobile.apteks.R;

import java.util.ArrayList;


public class LoadPageDrugTask extends AsyncTask<Void, Void, ArrayList<Drug>> {
    private View view;
    private String searchString;
    private FindDrugsAdapter adapter;
    private Context context;

    public LoadPageDrugTask(View v, FindDrugsAdapter a, String search, Context c) {
        this.view = v;
        this.adapter = a;
        this.searchString = search;
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Drug> doInBackground(Void... params) {
        try {
            Spinner spinner = (Spinner) this.view.findViewById(R.id.sortSpinner);
            String sort = this.view.getResources().getStringArray(R.array.sortListValues)[spinner.getSelectedItemPosition()];

            spinner = (Spinner) this.view.findViewById(R.id.districtSpinner);
            String district = this.view.getResources().getStringArray(R.array.districtListValues)[spinner.getSelectedItemPosition()];

            return Drug.findDrug(this.searchString, sort, district, this.adapter);
        }catch(Exception ex) {                  // TODO catch exceptions

        }
        return null;
    }



    @Override
    protected void onPostExecute(ArrayList<Drug> data) {
        super.onPostExecute(data);

        this.adapter.updateContent(data);

        ((FindDrugsActivity)this.context).resetLoadingFlag();
        this.view.findViewById(R.id.loadMoreFrameLayout).setVisibility(View.GONE);
    }
}
