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

public class ElementListFragment<T extends ListItem> extends Fragment {
    private List<T> data;
    private ElementListViewModel mViewModel;

    public static <T> ElementListFragment newInstance(List<T> data) {
        return new ElementListFragment(data);
    }

    public ElementListFragment(List<T> data) {
        this.data = data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_element_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ElementListViewModel.class);
        CustomListViewAdapter<T> customListViewAdapter = new CustomListViewAdapter<>(data, getActivity().getApplicationContext());
        ListView listView = (ListView) getView().findViewById(R.id.element_list_list_view);
        listView.setAdapter(customListViewAdapter);

        // TODO
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
