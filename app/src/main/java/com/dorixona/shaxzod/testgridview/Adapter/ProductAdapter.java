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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dorixona.shaxzod.testgridview.List.RegionList;
import com.dorixona.shaxzod.testgridview.MainActivity;
import com.dorixona.shaxzod.testgridview.Model.Product;
import com.dorixona.shaxzod.testgridview.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.text.TextUtils.substring;


public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private RegionList regionList;
    private RequestQueue queue;
    private int lastPosition = -1;
    public ProductAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView region = (TextView) view.findViewById(R.id.region);
        final Product p = getItem(position);
        if(p == null)
            return view;
        price.setText(p.getCost() + p.getCurrency());
        date.setText(substring(p.getCreated_at(),0,16));

        if(regionList != null)
            region.setText(regionList.getById(p.getRegion_id()));
        else
            region.setText(" ");

        int str_len = p.getName().length();
        name.setText((substring(p.getName(),0,((36 > str_len)? str_len: 36)) +((str_len > 36)?"...": " ")));
        final ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(R.drawable.holder_img);
        if((p.getImageBitmap() == null) && (p.getImage_id() != 0)){
            final String img_uri = MainActivity.baseurl + "image/" + p.getImage_id();
            final ImageRequest request = new ImageRequest(img_uri,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap response) {
                            if(response == null)
                                return;
                            p.setImageBitmap(response);
                            iv.setImageBitmap(response);
                        }
                    },
                    1000, 1000,
                    Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ProductAdapter", (error.getMessage() == null? "get null" : error.getMessage()));
                        }
                    });
            queue.add(request);
        }else{
            if(p.getImage_id() != 0 && p.getImageBitmap()!= null)
                iv.setImageBitmap(p.getImageBitmap());
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;
        return view;
    }

    public void setRegionList(RegionList regionList) {
        this.regionList = regionList;
    }

}
