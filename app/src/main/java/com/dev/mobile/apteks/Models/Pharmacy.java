package com.dev.mobile.apteks.Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by User on 29.04.2015.
 */
public class Pharmacy {
    private String pharmacyCity;
    private String pharmacyDistrict;
    private String pharmacyAddress;
    private String pharmacyPhone;


    private String pharmacyBuses;

    private static final int READ_TIMEOUT = 10000;

    public static Pharmacy getPharmacyInfo(String href) throws MalformedURLException, IOException {
        URL url = new URL("http://kraslek.ru" + href);

        // TODO refactor this!

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

        connection.disconnect();

//        Document doc = Jsoup.parse(sb.toString());
//
//        Elements elements = doc.select("tr > td.value");

        Pharmacy pharmacy = new Pharmacy();
//
//        if(elements.size() >= 5) {
//            pharmacy.setPharmacyCity(elements.get(0).text());
//            pharmacy.setPharmacyDistrict(elements.get(1).text());
//            pharmacy.setPharmacyAddress(elements.get(2).text());
//            pharmacy.setPharmacyPhone("+7(391)"+elements.get(3).text());
//            pharmacy.setPharmacyBuses(elements.get(4).text());
//        }

        return pharmacy;
    }

    public String getPharmacyCity() {
        return pharmacyCity;
    }

    public void setPharmacyCity(String pharmacyCity) {
        this.pharmacyCity = pharmacyCity;
    }

    public String getPharmacyDistrict() {
        return pharmacyDistrict;
    }

    public void setPharmacyDistrict(String pharmacyDistrict) {
        this.pharmacyDistrict = pharmacyDistrict;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyPhone() {
        return pharmacyPhone;
    }

    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    public String getPharmacyBuses() {
        return pharmacyBuses;
    }

    public void setPharmacyBuses(String pharmacyBuses) {
        this.pharmacyBuses = pharmacyBuses;
    }


}
