package com.dev.mobile.apteks.data.drugs.models;

import com.dev.mobile.apteks.Adapters.FindDrugsAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class Drug {
    private static final String SEARCH_URL = "http://kraslek.ru/search?query=";
    private static final String SUGGESTED_URL = "http://kraslek.ru/search/suggest?limit=5&q=";


    private static final int READ_TIMEOUT = 10000;
    private static boolean emptyAnswerFlag;

    private String drugName;
    private String drugPrice;
    private String pharmacyName;
    private String pharmacyAddress;
    private String pharmacyHref;

    public static ArrayList<String> getSuggestedDrugs(String search) throws UnsupportedEncodingException, MalformedURLException, IOException {
        if(search == null || search.length() == 0) {
            return null;
        }

        search = URLEncoder.encode(search, "UTF-8");

        URL url = new URL(SUGGESTED_URL + search);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        connection.setRequestMethod("GET");

        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "text/html");
        connection.setRequestProperty("Connection", "Close");

        connection.setReadTimeout(READ_TIMEOUT);

        connection.setDoInput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String tmp;

        ArrayList<String> result = new ArrayList<String>();

        int i = 0;

        while ((tmp = br.readLine()) != null && i++ < 5) {
            result.add(tmp);
            //sb.append(tmp);
        }

        connection.disconnect();

        return result;
    }


    public static ArrayList<Drug> findDrug(String searchName, String sort, String district, FindDrugsAdapter adapter)
            throws UnsupportedEncodingException, MalformedURLException, IOException {

        emptyAnswerFlag = false;

        if (searchName == null || searchName.length() == 0) {
            return null;
        }

        // encode search string
        searchName = URLEncoder.encode(searchName, "UTF-8");

        // TODO add districts and sort
        String urlString = SEARCH_URL
                + searchName
                + "&city=1"
                + "&area=" + district
                + "&sort=" + sort
                + "&page=" + Integer.toString(adapter.increasePageNum());

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("Accept-Encoding", "deflate");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "text/html");
        connection.setRequestProperty("Connection", "Close");

        connection.setReadTimeout(READ_TIMEOUT);

        connection.setDoInput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String tmp;

        while ((tmp = br.readLine()) != null) {
            sb.append(tmp);
        }

        if (connection != null) {
            connection.disconnect();
        }

//        Document doc = Jsoup.parse(sb.toString());
//        Elements mainElements = doc.select("ol[start] > li");

        ArrayList<Drug> drugList;

        if(adapter == null) {
            drugList = new ArrayList<Drug>();
        } else {
            drugList = adapter.getData();
        }

//        if (mainElements.size() > 0) {
//
//            for (int i = 0; i < mainElements.size(); i++) {
//
//                Drug drug = new Drug();
//
//                // TODO use constructor Drug ???
//
//                Elements elements;
//                Element element;
//
//                element = mainElements.get(i).select("h3").first();
//
//                if (element != null) {
//                    drug.setDrugName(element.text());
//                }
//
//                element = mainElements.get(i).select("span.price").first();
//
//                if (element != null) {
//                    drug.setDrugPrice(element.text());
//                }
//
//                elements = mainElements.get(i).select("a");
//
//                if (elements.size() > 2) {
//                    drug.setPharmacyName(elements.get(0).text());
//                    drug.setPharmacyHref(elements.get(0).attr("href"));
//                    drug.setPharmacyAddress(elements.get(1).text());
//                }
//
//
//                drugList.add(drug);
//            }
//        } else {  // need for no "loadMore" action
//            emptyAnswerFlag = true;
//        }

        return drugList;
    }

    public static boolean getEmptyAnswerFlag () {
        return emptyAnswerFlag;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(String drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPharmacyHref() {
        return pharmacyHref;
    }

    public void setPharmacyHref(String pharmacyHref) {
        this.pharmacyHref = pharmacyHref;
    }
}
