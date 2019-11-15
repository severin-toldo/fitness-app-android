package com.stoldo.fitness_app_android.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.stoldo.fitness_app_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementListFragment extends Fragment {

    private ElementListViewModel mViewModel;

    private String input1;

    public static ElementListFragment newInstance(String input1) {
        return new ElementListFragment(input1);
    }

    public ElementListFragment(String input1) {
        this.input1 = input1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.element_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ElementListViewModel.class);

        List<ListElementFragment> frags = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ListElementFragment frag = ListElementFragment.newInstance("Fragment " + i);
            frags.add(frag);
        }

        ArrayAdapter<ListElementFragment> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, frags);
        ListView listView = (ListView) getView().findViewById(R.id.element_list_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListElementFragment frag = ListElementFragment.newInstance("New Frag " + position);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.element_list_container, frag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
