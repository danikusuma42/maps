package com.example.mymaps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlacejson)
    {
        HashMap<String,String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longtitude = "";
        String reference = "";

        try{
            if (googlePlacejson.isNull("name")) {

                    placeName = googlePlacejson.getString("name");


            }
            if (!googlePlacejson.isNull("vicinity"))
            {
                vicinity = googlePlacejson.getString("vicinity");
            }
            latitude = googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longtitude = googlePlacejson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlacejson.getString("reference");

            googlePlacejson.put("place_name", placeName);
            googlePlacesMap.put("vicinity",vicinity);
            googlePlacesMap.put("lat",latitude);
            googlePlacesMap.put("lng",longtitude);
            googlePlacesMap.put("reference",reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;
    }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i<count; i++)
        {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  placeList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject ;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  getPlaces(jsonArray);
    }
}
