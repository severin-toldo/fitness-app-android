package com.stoldo.fitness_app_android.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stoldo.fitness_app_android.R;

public class ListElementFragment extends Fragment {

    private ListElementViewModel mViewModel;

    private String input1;

    private ListElementFragment instance = this;

    public static ListElementFragment newInstance(String input1) {
        return new ListElementFragment(input1);
    }

    public ListElementFragment(String input1) {
        this.input1 = input1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_element, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListElementViewModel.class);

        ConstraintLayout listElementContainer = (ConstraintLayout) getView().findViewById(R.id.list_element_background);
        listElementContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().hide(instance).commit();
            }
        });
    }

//    public void onButtonClick(View view) {
//    }
}
