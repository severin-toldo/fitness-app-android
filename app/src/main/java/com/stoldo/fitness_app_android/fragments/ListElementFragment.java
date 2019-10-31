package com.stoldo.fitness_app_android.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stoldo.fitness_app_android.R;

public class ListElementFragment extends Fragment {

    private ListElementViewModel mViewModel;

    private String input1;

    public static ListElementFragment newInstance(String input1) {
        return new ListElementFragment(input1);
    }

    public ListElementFragment(String input1) {
        this.input1 = input1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.list_element_fragment, container, false);
        TextView textView = (TextView) myFragmentView.findViewById(R.id.message);
        textView.setText(input1);

        return myFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListElementViewModel.class);
        // TODO: Use the ViewModel
    }

}
