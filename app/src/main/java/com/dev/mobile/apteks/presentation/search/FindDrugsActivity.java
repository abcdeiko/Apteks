package com.dev.mobile.apteks.presentation.search;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.dev.mobile.apteks.AboutPharmacyActivity;
import com.dev.mobile.apteks.Adapters.DrugAutoCompleteAdapter;
import com.dev.mobile.apteks.Adapters.FindDrugsAdapter;
import com.dev.mobile.apteks.Components.DelayedAutoCompleteTextView;
import com.dev.mobile.apteks.data.drugs.models.Drug;
import com.dev.mobile.apteks.R;
import com.dev.mobile.apteks.Tasks.FindDrugTask;
import com.dev.mobile.apteks.Tasks.LoadPageDrugTask;


public class FindDrugsActivity extends AppCompatActivity {
    private final int MIN_CHAR_TO_SUGGEST = 3;

    private FindDrugsAdapter adapter;
    private DrugAutoCompleteAdapter suggestAdapter;

    private boolean loadingFlag = false;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // TODO create landscape layouts

        setContentView(R.layout.activity_find_drugs);

        DelayedAutoCompleteTextView searchTextView = (DelayedAutoCompleteTextView) findViewById(R.id.textSearchName);


        this.suggestAdapter = new DrugAutoCompleteAdapter(this);

        searchTextView.setAdapter(this.suggestAdapter);
        searchTextView.setThreshold(MIN_CHAR_TO_SUGGEST);
        searchTextView.setLoadingIndicator((ProgressBar) findViewById(R.id.autocompleteProgressBar));



        ListView lsView = (ListView) findViewById(R.id.findDrugsListView);

        View footer = getLayoutInflater().inflate(R.layout.load_more, lsView, false);
        lsView.addFooterView(footer);


        this.adapter = new FindDrugsAdapter(this);

        lsView.setAdapter(this.adapter);

        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);

        // TODO refactor this
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new FindDrugTask(findViewById(android.R.id.content), adapter, searchString).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner = (Spinner) findViewById(R.id.districtSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new FindDrugTask(findViewById(android.R.id.content), adapter, searchString).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final EditText editTextSearching = (EditText) findViewById(R.id.textSearchName);

        editTextSearching.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    searchString = editTextSearching.getText().toString();

                    new FindDrugTask(findViewById(android.R.id.content), adapter, searchString).execute();

                    return true;
                }
                return false;
            }
        });

        ListView lsView = (ListView) findViewById(R.id.findDrugsListView);


        lsView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 1) {
                    if (!loadingFlag && !Drug.getEmptyAnswerFlag()) {
                        loadingFlag = true;

                        findViewById(R.id.loadMoreFrameLayout).setVisibility(View.VISIBLE);

                        new LoadPageDrugTask(findViewById(android.R.id.content), adapter, searchString, FindDrugsActivity.this).execute();

                    }
                }
            }
        });

        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FindDrugsActivity.this, AboutPharmacyActivity.class);

                String pharmacyName = ((Drug)parent.getItemAtPosition(position)).getPharmacyName();
                String pharmacyHref = ((Drug)parent.getItemAtPosition(position)).getPharmacyHref();

                intent.putExtra("pharmacyName", pharmacyName);
                intent.putExtra("pharmacyHref", pharmacyHref);

                startActivity(intent);
            }
        });
    }

    public void resetLoadingFlag() {
        this.loadingFlag = false;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.clearSearchFieldButton) {
            ((EditText) findViewById(R.id.textSearchName)).setText("");
        }
    }
}
