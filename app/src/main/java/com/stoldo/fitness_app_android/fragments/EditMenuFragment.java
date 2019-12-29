package com.stoldo.fitness_app_android.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;

public class EditMenuFragment extends Fragment {
    private Observable observable = new Observable<ActionEvent>();
    private boolean hasAddButton;

    // layout components
    private ImageButton editButton;
    private ImageButton confirmButton;
    private ImageButton addButton;
    private ImageButton cancelButton;


    public static EditMenuFragment newInstance(Subscriber<ActionEvent> subscriber, boolean hasAddButton) {
        return new EditMenuFragment(subscriber, hasAddButton);
    }

    public EditMenuFragment(Subscriber<ActionEvent> subscriber, boolean hasAddButton) {
        this.observable.subscribe(subscriber);
        this.hasAddButton = hasAddButton;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpComponentsFromLayout(getView());
    }

    private void setUpComponentsFromLayout(View view) {
        editButton = view.findViewById(R.id.edit_button);
        editButton.setOnClickListener((View v) ->  {
            setVisibility(View.GONE, View.VISIBLE, hasAddButton ? View.VISIBLE : View.GONE, View.VISIBLE);
            observable.notifySubscribers(new ActionEvent(true, ActionType.EDIT));
        });

        confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener((View v) ->  {
            setDefaultVisibility();
            observable.notifySubscribers(new ActionEvent(false, ActionType.CONFIRM));
        });

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener((View v) ->  {
            observable.notifySubscribers(new ActionEvent(true, ActionType.ADD));
        });

        cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener((View v) -> {
            setDefaultVisibility();
            observable.notifySubscribers(new ActionEvent(false, ActionType.CANCEL));
        });

        setDefaultVisibility();
    }

    private void setDefaultVisibility() {
        setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    private void setVisibility(int edit, int confirm, int add, int cancel) {
        editButton.setVisibility(edit);
        confirmButton.setVisibility(confirm);
        addButton.setVisibility(add);
        cancelButton.setVisibility(cancel);
    }
}
