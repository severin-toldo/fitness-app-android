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
import com.stoldo.fitness_app_android.model.data.ListViewData;
import com.stoldo.fitness_app_android.model.Observable;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.enums.ActionType;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.util.LogUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;


public class ListViewFragment<S extends Subscriber<ActionEvent>, I extends ListItem> extends Fragment implements Subscriber<ActionEvent> {
    private ListViewData<S, I> listViewData;

    private boolean editMode = false;
    private ListView listView;
    private Observable observable = new Observable<ActionEvent>();

    @Getter
    private List<I> baseItems; // Alle gespeicherten items


    public static ListViewFragment newInstance(@lombok.NonNull ListViewData listViewData) {
        return new ListViewFragment<>(listViewData);
    }

    public ListViewFragment(ListViewData listViewData) {
        this.listViewData = listViewData;
        this.baseItems = new ArrayList<>(listViewData.getItems());
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
    public void update(ActionEvent data) {
        editMode = data.getEditMode();

        switch (data.getActionType()) {
            case EDIT:
                onEdit();
                break;
            case CANCEL:
                onCancel();
                break;
            case ADD:
                onAdd();
                break;
            case CONFIRM:
                onConfirm();
                break;
            case REMOVE:
                onRemove(data.getItemIndex());
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
                    LogUtil.logErrorAndExit(ErrorCode.E1000.getErrorMsg(), getClass(), e);
                }
            });
        }
    }

    // TODO tahts why after an add the click handlers dont work anymore
    public void updateItems(List<I> data){
        CustomListViewAdapter<I> customListViewAdapter = new CustomListViewAdapter<>(data, listViewData.getItemLayout(), getActivity().getApplicationContext(), this, editMode);
        listView = getView().findViewById(R.id.element_list_list_view);
        listView.setAdapter(customListViewAdapter);
    }

    private void onEdit() {
        finalizeAction(listViewData.getItems(), listViewData.getEditItemClickMethod(), ActionType.EDIT);
    }

    private void onCancel() {
        listViewData.setItems(new ArrayList<I>(baseItems));
        finalizeAction(baseItems, listViewData.getDefaultItemClickMethod(), ActionType.CANCEL);
    }

    // TODO rework
    private void onAdd() {
        //editedItems = new ArrayList<>(listViewData.getItems());
        finalizeAction(listViewData.getItems(), listViewData.getEditItemClickMethod(), ActionType.ADD);
    }

    private void onConfirm() {
        baseItems = new ArrayList<I>(listViewData.getItems());
        finalizeAction(baseItems, listViewData.getDefaultItemClickMethod(), ActionType.CONFIRM);
        // save stuff, fragment or activy jursitiction? i think both
    }

    private void onRemove(int itemIndex) {
        List<I> items = listViewData.getItems();
        items.remove(itemIndex);
        listViewData.setItems(items);
        finalizeAction(listViewData.getItems(), listViewData.getEditItemClickMethod(), ActionType.REMOVE);
    }

    /**
     * Sets up a new listview with the new data and notifies the subscriber of the action
     * */
    private void finalizeAction(List<I> items, Method clickMethod, ActionType actionType) {
        setUpListView(items, clickMethod);
        observable.notifySubscribers(new ActionEvent(editMode, actionType));
    }
}
