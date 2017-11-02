package com.dorixona.shaxzod.testgridview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dorixona.shaxzod.testgridview.Adapter.CategoryAdapter;
import com.dorixona.shaxzod.testgridview.Adapter.ProductAdapter;
import com.dorixona.shaxzod.testgridview.Adapter.RegionAdapter;
import com.dorixona.shaxzod.testgridview.List.CategoryList;
import com.dorixona.shaxzod.testgridview.List.ProductList;
import com.dorixona.shaxzod.testgridview.List.RegionList;
import com.dorixona.shaxzod.testgridview.Model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static GridView gv;
    private ProductList productList;
    private static RegionList regionList;
    private static CategoryList categoryList;
    private View categories_view;
    private static View regions_view = null;
    private View grid_item_long_press;
    static LinearLayout errorLayout;
    public static ProgressDialog progress;
    public static String baseurl = "http://192.168.1.106/api/";
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "Stop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        progress = new ProgressDialog(this);
        progress.setMessage("Yuklanmoqda....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        gv =  (GridView) findViewById(R.id.gridview);
        errorLayout = (LinearLayout) findViewById(R.id.errorLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.smoothScrollToPosition(0);
            }
        });
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View v = nv.getHeaderView(0);
        TextView login_btn = (TextView) v.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                Product product = (Product) parent.getItemAtPosition(position);
                TextView tv = (TextView) grid_item_long_press.findViewById(R.id.textView3);
                ImageView iv = (ImageView) grid_item_long_press.findViewById(R.id.imageView2);
                tv.setText(product.getName());
                iv.setImageBitmap(product.getImageBitmap());
                if(grid_item_long_press.getParent()!= null)
                    ((ViewGroup)grid_item_long_press.getParent()).removeView(grid_item_long_press);
                builder.setView(grid_item_long_press);
                builder.setCancelable(true);
                builder.show();
                return false;
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ViewProduct.class);
                Category c = new Category();
                intent.putExtra("product",product);
                startActivity(intent);
            }
        });
        gv.getOnItemClickListener();
        GetContent gc = new GetContent();
        gc.execute(baseurl + "products");

        final LayoutInflater inflater = this.getLayoutInflater();
        categories_view = inflater.inflate(R.layout.categories, null);
        regions_view = inflater.inflate(R.layout.regions, null);
        grid_item_long_press = inflater.inflate(R.layout.grid_view_long_press, null);

        Button btn = (Button) findViewById(R.id.category_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Categories");
                builder.setCancelable(true);
                if(categories_view.getParent()!= null)
                    ((ViewGroup)categories_view.getParent()).removeView(categories_view);
                builder.setView(categories_view);
                builder.show();
            }
        });
        Button btn1 = (Button) findViewById(R.id.hudud_btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Regions");
                builder.setCancelable(true);
                if(regions_view.getParent()!= null)
                    ((ViewGroup)regions_view.getParent()).removeView(regions_view);
                builder.setView(regions_view);
                builder.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), FindActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find) {
            Intent intent = new Intent(getApplicationContext(), FindActivity.class);
            startActivity(intent);
        }
        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateDisplay(String resource){
        gv.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        try {
            JSONObject jo = new JSONObject(resource);
            JSONArray ja_products = jo.getJSONArray("products");
            JSONArray ja_categories = jo.getJSONArray("categories");
            JSONArray ja_regions = jo.getJSONArray("regions");

            categoryList = new CategoryList(this);
            categoryList.LoadFromJSONArray(ja_categories);


            regionList = new RegionList(this);
            regionList.LoadFromJSONArray(ja_regions);

            productList = new ProductList(this);
            productList.LoadFromJSONArray(ja_products);


            ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), R.layout.grid_item,productList);
            productAdapter.setRegionList(regionList);

            CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(),R.layout.category_item, categoryList);
            RegionAdapter regionAdapter = new RegionAdapter(getApplicationContext(), R.layout.region_item, regionList);

            ListView lv = (ListView) categories_view.findViewById(R.id.list);
            lv.setAdapter(categoryAdapter);

            lv = (ListView) regions_view.findViewById(R.id.regions);
            lv.setAdapter(regionAdapter);

            gv.setAdapter(productAdapter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                gv.setOnScrollChangeListener(new View.OnScrollChangeListener(){

                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void notifyError() {
        gv.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }
    public void startLoading(){
        progress.show();
    }
    public void finishLoading(){
        progress.dismiss();
    }
    public void updateDisplayButton(View v){
        GetContent gc = new GetContent();
        gc.execute( baseurl + "products");
    }
    public class GetContent extends AsyncTask<String,String, String>{
        @Override
        protected void onPreExecute() {
            startLoading();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HTTPRequest request = new HTTPRequest(MainActivity.this);
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

    public static RegionList getRegionList() {
        return regionList;
    }
    public static CategoryList getCategoryList(){return categoryList;}
}
