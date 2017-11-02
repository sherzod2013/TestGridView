package com.dorixona.shaxzod.testgridview.List;

import android.content.Context;

import com.dorixona.shaxzod.testgridview.Model.Category;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Shaxzod on 24.07.2017.
 */

public class CategoryList extends ArrayList<Category> {
    private Context context;
    public CategoryList(Context context){
        this.context = context;
    }
    public void LoadFromJSONArray(JSONArray j){
        Gson gson = new Gson();
        Category[] categories = gson.fromJson(String.valueOf(j), Category[].class);
        for (int i=0; i< categories.length; i++)
            add(categories[i]);
    }
}
