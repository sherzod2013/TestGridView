package com.dorixona.shaxzod.testgridview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dorixona.shaxzod.testgridview.Adapter.ProductAdapter;
import com.dorixona.shaxzod.testgridview.List.ImageList;
import com.dorixona.shaxzod.testgridview.List.ProductList;
import com.dorixona.shaxzod.testgridview.Model.Image;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {
    private String baseUrl = MainActivity.baseurl;
    private CarouselView carouselView;
    private RequestQueue queue;
    private ImageList imagelist = new ImageList(this);
    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageBitmap(imagelist.get(position).getImage());
        }
    };
    private Product product;
    TextView tv_name;
    TextView tv_info;
    TextView tv_price;
    TextView tv_address;
    TextView tv_count_view;
    TextView tv_product_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv_name = (TextView) findViewById(R.id.name);
        tv_info = (TextView) findViewById(R.id.info);
        tv_price = (TextView) findViewById(R.id.price);
        tv_address = (TextView) findViewById(R.id.address);
        tv_count_view = (TextView) findViewById(R.id.count_view);
        tv_product_id = (TextView) findViewById(R.id.product_id);
        this.queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        product = (Product) intent.getParcelableExtra("product");
        int id = (int) product.getId();
        installProduct(product);

        GetContent gc = new GetContent();
        gc.execute(baseUrl + "getproduct/" + id);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        Image i = new Image();
        i.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.holder_img));
        imagelist.add(i);
        carouselView.setPageCount(imagelist.size());
        carouselView.setImageListener(imageListener);


    }


    public class GetContent extends AsyncTask<String,String, String> {
        @Override
        protected void onPreExecute() {
            startLoading();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HTTPRequest request = new HTTPRequest(ViewProduct.this);
                return request.Get(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            finishLoading();
            super.onPostExecute(s);
            if(s != null)
                updateDisplay(s);
            else
                notifyError();
        }

    }

    private void finishLoading() {
        Log.d("status","finish loading");
    }

    private void startLoading() {
        Log.d("status","start loading");
    }

    private void updateDisplay(String result) {
        try{
            JSONObject jo = new JSONObject(result);
            JSONArray ja_related_products = jo.getJSONArray("related_products");
            JSONArray ja_images = jo.getJSONArray("images");
            loadImages(ja_images);

            JSONObject jo_product = jo.getJSONObject("product");
            Gson gson = new Gson();
            Product product = gson.fromJson(String.valueOf(jo_product), Product.class);
            installProduct(product);

            JSONObject jo_user = jo.getJSONObject("user");
            GridView gv = (GridView) findViewById(R.id.related_products);

            ProductList productList = new ProductList(this);
            productList.LoadFromJSONArray(ja_related_products);

            ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), R.layout.grid_item,productList);
            productAdapter.setRegionList(MainActivity.getRegionList());

            gv.setAdapter(productAdapter);
            int size = productList.size();
            gv.setNumColumns(size);

            // Calculated single Item Layout Width for each grid element ....
            int width = 180;

            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;

            int totalWidth = (int) (width * size * density);
            int singleItemWidth = (int) (width * density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

            gv.setLayoutParams(params);
            gv.setColumnWidth(singleItemWidth);
            gv.setHorizontalSpacing(2);
            gv.setStretchMode(GridView.STRETCH_SPACING);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product product = (Product) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), ViewProduct.class);
                    intent.putExtra("product",product);
                    startActivity(intent);
                }
            });
        }catch (Exception e){

        }

    }
    private void installProduct(Product product){
        if( product == null)
            return;
        tv_name.setText(product.getName());
        tv_info.setText(product.getInfo());
        tv_price.setText(product.getCost());
        tv_address.setText(product.getAddress());
        tv_product_id.setText("Номер объявления: "+product.getId());
        tv_count_view.setText("Просмотры: "+product.getCount_view());
    }
    private void loadImages(JSONArray ja_images) {
        carouselView.setPageCount(0);
        imagelist.clear();
        imagelist.LoadFromJSONArray(ja_images);
        for (int i=0; i<imagelist.size(); i++){
            final Image image = imagelist.get(i);
            image.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.holder_img));
            final String img_uri = baseUrl + "image/" + image.getId();
            final ImageRequest request = new ImageRequest(img_uri,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            if(response == null)
                                return;
                            image.setImage(response);
                            carouselView.setPageCount(imagelist.size());
                        }
                    },
                    1000, 1000,
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "not downloaded", Toast.LENGTH_SHORT).show();

                            Log.d("ViewProduct", (error.getMessage() == null? "get null from image" : error.getMessage()));
                        }
                    });
            queue.add(request);
        }


    }

    private void notifyError() {
        Log.d("status","error occurred");
    }
}
