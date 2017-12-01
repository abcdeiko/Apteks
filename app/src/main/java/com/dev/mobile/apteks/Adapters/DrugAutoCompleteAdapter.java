package com.dev.mobile.apteks.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dev.mobile.apteks.data.drugs.models.Drug;

import java.util.ArrayList;

/**
 * Created by User on 15.05.2015.
 */
public class DrugAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private ArrayList<String> listData;

    public DrugAutoCompleteAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return this.listData.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(this.mContext).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        ((TextView) view.findViewById(android.R.id.text1)).setText((String)this.getItem(i));


        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence != null) {
                    try {
                        ArrayList<String> data = Drug.getSuggestedDrugs(charSequence.toString());

                        filterResults.values = data;
                        filterResults.count = data.size();


                        return filterResults;

                    } catch(Exception ex) {

                    }
                }

                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults != null && filterResults.count > 0) {
                    listData = (ArrayList<String>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
