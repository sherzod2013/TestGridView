package com.dorixona.shaxzod.testgridview.List;

import android.content.Context;

import com.dorixona.shaxzod.testgridview.Model.Product;
import com.dorixona.shaxzod.testgridview.Model.Region;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaxzod on 24.07.2017.
 */

public class RegionList extends ArrayList<Region> {
    private Context context;
    Map<Integer,String> mapRegion =new HashMap<Integer,String>();

    public RegionList(Context context){
        this.context = context;
    }
    public void LoadFromJSONArray(JSONArray j){
        Gson gson = new Gson();
        Region[] regions = gson.fromJson(String.valueOf(j), Region[].class);
        for (int i=0; i< regions.length; i++){
            add(regions[i]);
            mapRegion.put(regions[i].getId(), regions[i].getName_ru());
        }
    }
    public String getById(int d){
        return mapRegion.get(d);
    }
}
