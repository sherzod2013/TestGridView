package com.dorixona.shaxzod.testgridview;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dorixona.shaxzod.testgridview.Adapter.RegionAdapter;
import com.dorixona.shaxzod.testgridview.List.CategoryList;
import com.dorixona.shaxzod.testgridview.List.RegionList;
import com.dorixona.shaxzod.testgridview.Model.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindActivity extends AppCompatActivity {
    View regions_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        final AutoCompleteTextView region = (AutoCompleteTextView) findViewById(R.id.find_region);

        region.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ArrayList<Region> regions = MainActivity.getRegionList();
                    final int size = regions.size();
                    String[] regions_arr = new String[size];
                    for (int i=0; i<size; i++)
                        regions_arr[i] = regions.get(i).getName_ru();
                    CreateDialog(regions_arr, size, region);
                }
            }
        });
        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Region> regions = MainActivity.getRegionList();
                final int size = regions.size();
                String[] regions_arr = new String[size];
                for (int i=0; i<size; i++)
                    regions_arr[i] = regions.get(i).getName_ru();
                CreateDialog(regions_arr, size, region);
            }
        });
        final AutoCompleteTextView category = (AutoCompleteTextView) findViewById(R.id.category);

        category.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    CategoryList categories = MainActivity.getCategoryList();
                    final int size = categories.size();
                    String[] category_arr = new String[size];
                    for (int i=0; i<size; i++)
                        category_arr[i] = categories.get(i).getName_ru();
                    CreateDialog(category_arr, size, category);
                }
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryList categories = MainActivity.getCategoryList();
                final int size = categories.size();
                String[] category_arr = new String[size];
                for (int i=0; i<size; i++)
                    category_arr[i] = categories.get(i).getName_ru();
                CreateDialog(category_arr, size, category);
            }
        });
    }

    public void CreateDialog(String[] list, final int size, final AutoCompleteTextView tv){
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);

        // Boolean array for initial selected items
        final boolean[] checkedRegions = new boolean[size];
        for (int i=0; i<size; i++)
        {
            checkedRegions[i] = false;
        }
        // Convert the color array to list
        final List<String> colorsList = Arrays.asList(list);


        builder.setMultiChoiceItems(list, checkedRegions, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedRegions[which] = isChecked;

                // Get the current focused item
                String currentItem = colorsList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Районы");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                tv.setText("");
                for (int i = 0; i<size; i++){
                    boolean checked = checkedRegions[i];
                    if (checked) {
                        tv.setText(tv.getText() + colorsList.get(i) + ",");
                    }
                }
            }
        });

        // Set the negative/no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}
