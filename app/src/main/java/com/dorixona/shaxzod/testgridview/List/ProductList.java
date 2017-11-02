package com.dorixona.shaxzod.testgridview.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dorixona.shaxzod.testgridview.Adapter.ProductAdapter;
import com.dorixona.shaxzod.testgridview.MainActivity;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.dorixona.shaxzod.testgridview.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProductList extends ArrayList<Product> {
    private Context context;
    public ProductList(Context context){
        this.context = context;
    }
    public void LoadFromJSONArray(JSONArray j){
        Gson gson = new Gson();
        Product[] products = gson.fromJson(String.valueOf(j), Product[].class);
        for (int i=0; i< products.length; i++)
            add(products[i]);
    }

}
