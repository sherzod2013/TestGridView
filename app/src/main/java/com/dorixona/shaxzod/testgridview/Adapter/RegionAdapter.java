package com.dorixona.shaxzod.testgridview.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.dorixona.shaxzod.testgridview.Model.Region;
import com.dorixona.shaxzod.testgridview.R;

import java.util.List;

import static android.text.TextUtils.substring;

/**
 * Created by Shaxzod on 26.07.2017.
 */

public class RegionAdapter extends ArrayAdapter<Region> {
    private Context context;

    public RegionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Region> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.region_item, parent, false);

        TextView tv = (TextView) view.findViewById(R.id.name);
        Region region = getItem(position);
        tv.setText(region.getName_ru());

        return view;
    }
}
