package com.dorixona.shaxzod.testgridview.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dorixona.shaxzod.testgridview.Model.Category;
import com.dorixona.shaxzod.testgridview.R;

import java.util.List;


public class CategoryAdapter extends ArrayAdapter<Category> {

    private Context context;

    public CategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.category_item, parent, false);
        TextView tv = (TextView) view.findViewById(R.id.textView2);
        Category category = getItem(position);
        tv.setText(category.getName_ru());
        return view;
    }
}
