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
import android.widget.ListView;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.CustomListViewAdapter;
import com.stoldo.fitness_app_android.model.ListItem;
import com.stoldo.fitness_app_android.model.Subscriber;

import java.util.List;

// TODO create fragment data classes so we dont have 100 args constructors
public class ListViewFragment<T extends ListItem> extends Fragment implements Subscriber {
    private List<T> data;
    private AdapterView.OnItemClickListener defaultClickListener;
    private ListViewModel mViewModel;
    private boolean editMode = false;
    private ListView listView;

    public static <T> ListViewFragment newInstance(List<T> data, AdapterView.OnItemClickListener defaultClickListener) {
        return new ListViewFragment(data, defaultClickListener);
    }

    public ListViewFragment(List<T> data, AdapterView.OnItemClickListener defaultClickListener) {
        this.data = data;
        this.defaultClickListener = defaultClickListener;
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
        setUpListView();
        setUpEditMenu();
    }

    @Override
    public void update(String action) {
        Log.d("MYDEBUG", "Got notified " + action);
    }

    private void onEdit() {
        this.editMode = true;
        // TODO display remove buttons and set the edit click listener

    }

    private void onCancel() {
        listView.setOnItemClickListener(defaultClickListener);
        this.editMode = false;
        // dont save anything
    }

    private void onAdd() {
        // TODO open add form fragment and append to list.
    }

    private void onConfirm() {

    }

    private void setUpListView() {
        CustomListViewAdapter<T> customListViewAdapter = new CustomListViewAdapter<>(data, getActivity().getApplicationContext());
        listView = getView().findViewById(R.id.element_list_list_view);
        listView.setAdapter(customListViewAdapter);
        listView.setOnItemClickListener(defaultClickListener);
    }

    private void setUpEditMenu() {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.list_view_container, EditMenuFragment.newInstance(this, true))
                .commit();
    }
}
