package com.dorixona.shaxzod.testgridview.List;

import android.content.Context;

import com.dorixona.shaxzod.testgridview.Model.Image;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Shaxzod on 08.08.2017.
 */

public class ImageList extends ArrayList<Image> {
    private Context context;
    public ImageList(Context context){
        this.context = context;
    }
    public void LoadFromJSONArray(JSONArray j){
        Gson gson = new Gson();
        Image[] images = gson.fromJson(String.valueOf(j), Image[].class);
        for (int i=0; i< images.length; i++)
            add(images[i]);
    }
}
