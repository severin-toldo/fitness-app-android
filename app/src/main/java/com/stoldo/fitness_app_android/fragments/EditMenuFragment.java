package com.stoldo.fitness_app_android.fragments;

import androidx.lifecycle.ViewModelProviders;

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
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.util.HashMap;
import java.util.Map;

public class EditMenuFragment extends Fragment {
    private EditMenuViewModel mViewModel;
    private Observable observable = new Observable();
    private boolean hasAddButton;

    // layout components
    private ImageButton editButton;
    private ImageButton confirmButton;
    private ImageButton addButton;
    private ImageButton cancelButton;


    public static EditMenuFragment newInstance(Subscriber subscriber, boolean hasAddButton) {
        return new EditMenuFragment(subscriber, hasAddButton);
    }

    public EditMenuFragment(Subscriber subscriber, boolean hasAddButton) {
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
        mViewModel = ViewModelProviders.of(this).get(EditMenuViewModel.class);
        setUpComponentsFromLayout(getView());
    }

    private void setUpComponentsFromLayout(View view) {
        editButton = view.findViewById(R.id.edit_button);
        editButton.setOnClickListener((View v) ->  {
            setVisibility(View.GONE, View.VISIBLE, hasAddButton ? View.VISIBLE : View.GONE, View.VISIBLE);
            observable.notifySubscribers(OtherUtil.getEditMenuEventMap("edit", true));
        });

        confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener((View v) ->  {
            setDefaultVisibility();
            observable.notifySubscribers(OtherUtil.getEditMenuEventMap("confirm", false));
        });

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener((View v) ->  {
            observable.notifySubscribers(OtherUtil.getEditMenuEventMap("add", true));
        });

        cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener((View v) -> {
            setDefaultVisibility();
            observable.notifySubscribers(OtherUtil.getEditMenuEventMap("cancel", false));
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
