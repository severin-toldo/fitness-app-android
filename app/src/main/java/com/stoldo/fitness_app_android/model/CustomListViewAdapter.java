package com.stoldo.fitness_app_android.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.stoldo.fitness_app_android.R;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter<T extends ListItem> extends ArrayAdapter<T> implements View.OnClickListener {
    private List<T> data = new ArrayList<>();
    private int lastPosition = -1;

    public CustomListViewAdapter(List<T> data, Context context) {
        super(context, data.get(0).getItemLayout(), data);
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        T dataModel = getItem(position);

        if (v.getId() == R.id.extra) {
            // TODO do this right
            Snackbar.make(v, "in adapter: " + dataModel.getExtra(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T dataModel = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(dataModel.getItemLayout(), parent, false);

        if (dataModel.getTitle() != null) {
            TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
            titleTextView.setText(dataModel.getTitle());
        }

        if (dataModel.getDescription() != null) {
            TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description);
            descriptionTextView.setText(dataModel.getDescription());
        }

        if (dataModel.getExtra() != null) {
            TextView extraTextView = (TextView) convertView.findViewById(R.id.extra);
            extraTextView.setText(dataModel.getExtra());
        }

        lastPosition = position;
        return convertView;
    }
}

