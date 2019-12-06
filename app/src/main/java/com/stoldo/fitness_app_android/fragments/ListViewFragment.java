package com.stoldo.fitness_app_android.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.CustomListViewAdapter;
import com.stoldo.fitness_app_android.model.ListItem;

import java.util.List;

public class ListViewFragment<T extends ListItem> extends Fragment {
    private List<T> data;
    private AdapterView.OnItemClickListener clickListener;
    private ListViewModel mViewModel;

    public static <T> ListViewFragment newInstance(List<T> data, AdapterView.OnItemClickListener clickListener) {
        return new ListViewFragment(data, clickListener);
    }

    public ListViewFragment(List<T> data, AdapterView.OnItemClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        CustomListViewAdapter<T> customListViewAdapter = new CustomListViewAdapter<>(data, getActivity().getApplicationContext());
        ListView listView = (ListView) getView().findViewById(R.id.element_list_list_view);
        listView.setAdapter(customListViewAdapter);
        listView.setOnItemClickListener(clickListener);
    }
}
