package com.stoldo.fitness_app_android.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter<T extends ListItem> extends ArrayAdapter<T> {
    private List<T> data = new ArrayList<>();
    private @LayoutRes int itemLayout;
    private Observable<ActionEvent> observable = new Observable<>();
    private boolean editMode = false;
    private int lastPosition = -1;


    public CustomListViewAdapter(List<T> data, @LayoutRes int itemLayout, Context context, Subscriber<ActionEvent> subscriber, boolean editMode) {
        super(context, itemLayout, data);
        this.data = data;
        this.itemLayout = itemLayout;
        this.editMode = editMode;
        observable.subscribe(subscriber);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);

        TextView removeButton = convertView.findViewById(R.id.removeButton);
        removeButton.setVisibility(editMode ? View.VISIBLE : View.GONE);
        removeButton.setTextColor(Color.RED);
        removeButton.setOnClickListener((View v) -> {
            observable.notifySubscribers(new ActionEvent(true, ActionType.REMOVE, position));
        });

        T dataModel = getItem(position);
        if (dataModel.getTitle() != null) {
            TextView titleTextView = convertView.findViewById(R.id.title);
            titleTextView.setText(dataModel.getTitle());
        }

        if (dataModel.getDescription() != null) {
            TextView descriptionTextView = convertView.findViewById(R.id.description);
            descriptionTextView.setText(dataModel.getDescription());
        }

        if (dataModel.getExtra() != null) {
            TextView extraTextView = convertView.findViewById(R.id.extra);
            extraTextView.setText(dataModel.getExtra());
        }

        lastPosition = position;
        return convertView;
    }
}

