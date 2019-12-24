package com.stoldo.fitness_app_android.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.CustomListViewAdapter;
import com.stoldo.fitness_app_android.model.ListViewData;
import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ListViewFragment<S extends Subscriber, I extends ListItem> extends Fragment implements Subscriber {
    private ListViewData<S, I> listViewData;

    private boolean editMode = false;
    private ListView listView;
    private List<I> editedItems;
    private Observable observable = new Observable();


    public static ListViewFragment newInstance(@lombok.NonNull ListViewData listViewData) {
        return new ListViewFragment<>(listViewData);
    }

    public ListViewFragment(ListViewData listViewData) {
        this.listViewData = listViewData;
        observable.subscribe(this.listViewData.getListViewSubscriber());
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
        setUpListView(listViewData.getItems(), listViewData.getDefaultItemClickMethod());
        setUpEditMenu();
    }

    @Override
    public void update(Map<String, Object> data) {
        editMode = (Boolean) data.get("editMode");
        // TODO is there nicer solution?
        switch ((String) data.get("action")) {
            case "edit":
                onEdit();
                break;
            case "cancel":
                onCancel();
                break;
            case "add":
                onAdd();
                break;
            case "confirm":
                onConfirm();
                break;
        }
    }

    private void setUpEditMenu() {
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.list_view_container, EditMenuFragment.newInstance(this, true))
                .commit();
    }

    private void setUpListView(List<I> data, Method clickMethod) {
        updateItems(data);

        if (clickMethod != null) {
            listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                try {
                    clickMethod.invoke(listViewData.getListViewSubscriber(), data.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void updateItems(List<I> data){
        CustomListViewAdapter<I> customListViewAdapter = new CustomListViewAdapter<>(data, listViewData.getItemLayout(), getActivity().getApplicationContext());
        listView = getView().findViewById(R.id.element_list_list_view);
        // TODO display or hide badges here (accoridng to editMode) --> Badeges are item layout jurisiticton! --> well only view but not display / hide
        listView.setAdapter(customListViewAdapter);
    }

    // TODO onRemove and add remove badges
    // TODO click on item crashes the app. find out if in edit mode or in normal mode
    private void onEdit() {
        editedItems = new ArrayList<>(listViewData.getItems());
        finalizeAction(editedItems, listViewData.getEditItemClickMethod(), "edit");
    }

    private void onCancel() {
        editedItems.clear();
        finalizeAction(listViewData.getItems(), listViewData.getDefaultItemClickMethod(), "cancel");
    }

    // TODO problem: if you add an item its only there in this component. However the click listener is inside the activity which doesnt knwo this new item! there for index exception,
    // TODO solution: move clicklisteners in this component and pass the activity or fragment
    private void onAdd() {
        finalizeAction(editedItems, listViewData.getEditItemClickMethod(), "add");
        // TODO somehow make and input output system
        // TODO open add form fragment and append to editedItems.
    }

    private void onConfirm() {
        listViewData.getItems().clear();
        listViewData.setItems(new ArrayList<>(editedItems));
        editedItems.clear();
        finalizeAction(listViewData.getItems(), listViewData.getDefaultItemClickMethod(), "confirm");
        // save stuff, fragment or activy jursitiction? i think both
    }

    /**
     * Sets up a new listview with the new data and notifies the subscribe of the action
     * */
    private void finalizeAction(List<I> items, Method clickMethod, String action) {
        setUpListView(items, clickMethod);
        observable.notifySubscribers(OtherUtil.getEditMenuEventMap(action, editMode));
    }
}
