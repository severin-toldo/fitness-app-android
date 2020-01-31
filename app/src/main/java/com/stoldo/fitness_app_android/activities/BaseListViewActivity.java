package com.stoldo.fitness_app_android.activities;

import android.os.Bundle;

import com.stoldo.fitness_app_android.fragments.FormFragment;
import com.stoldo.fitness_app_android.fragments.ListViewFragment;
import com.stoldo.fitness_app_android.model.abstracts.AbstractSyncService;
import com.stoldo.fitness_app_android.model.data.ListViewData;
import com.stoldo.fitness_app_android.model.data.events.ActionEvent;
import com.stoldo.fitness_app_android.model.interfaces.Entity;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.model.interfaces.Subscriber;
import com.stoldo.fitness_app_android.util.LogUtil;
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.util.List;

import lombok.Setter;

/**
 * Base Activity for all Activities with a list view.
 *
 * @param <A> Extending Activity
 * @param <I> Item Entity
 * @param <S> Item (Entity) Service
 */
public abstract class BaseListViewActivity<A extends Subscriber<ActionEvent>, I extends ListItem & Entity, S extends AbstractSyncService> extends BaseActivity implements Subscriber<ActionEvent>, Submitable {

    @Setter
    private int itemLayoutId;
    @Setter
    private int containerLayoutId;

    @Setter
    private Class<I> entityClass;

    protected ListViewFragment listViewFragment;
    protected S service;

    @Override
    public Object onSubmit(Object value) {
        I newEntity = (I)value;
        List<I> entities = listViewFragment.getItems();
        if (newEntity != null && !entities.contains(newEntity)){
            entities.add(newEntity);
        }
        listViewFragment.updateItems(entities);
        return null;
    }

    @Override
    public void update(ActionEvent data) {
        if (data != null) {
            switch (data.getActionType()) {
                case ADD:
                    setUpEditForm();
                    break;
                case CONFIRM:
                    saveEntities();
                    break;
            }
        }
    }

    public void editOnListItemClick(I clickedEntity){
        setUpEditForm(clickedEntity);
    }

    protected void initializeBaseAttributes(Class<I> entityClass, Class<S> serviceClass, int itemLayoutId, int containerLayoutId){
        this.entityClass = entityClass;
        this.setService(serviceClass);
        this.itemLayoutId = itemLayoutId;
        this.containerLayoutId = containerLayoutId;
    }

    /**
     * Sets the service based on the passed class
     *
     * @param serviceClass class of service to get
     * */
    protected void setService(Class<S> serviceClass){
        if(serviceClass != null){
            service = (S) OtherUtil.getSingletonInstance(serviceClass);
        }
    }

    /**
     * Sets up the edit form which opens when an item is clicked in edit mode
     * for a given entity
     *
     * @param entity entity to set up form for
     *
     * */
    protected void setUpEditForm(I entity) {
        FormFragment formFragment = FormFragment.newInstance(entity);
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(containerLayoutId, formFragment)
                .commitNow();
    }

    /**
     * Sets up an empty edit form which opens when an item is clicked in edit mode
     * */
    protected void setUpEditForm() {
        try {
            setUpEditForm(entityClass.newInstance());
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), getClass(), e);
        }
    }

    protected void setUpListView(Bundle savedInstanceState, List<I> exercises) throws NoSuchMethodException {
        ListViewData<A, I> listViewData = new ListViewData<>();
        listViewData.setItems(exercises);
        listViewData.setItemLayout(itemLayoutId);
        listViewData.setListViewSubscriber((A)this);
        listViewData.setDefaultItemClickMethod(this.getClass().getDeclaredMethod("defaultOnListItemClick", entityClass));
        listViewData.setEditItemClickMethod(this.getClass().getMethod("editOnListItemClick", ListItem.class));

        if (savedInstanceState == null) {
            listViewFragment = ListViewFragment.newInstance(listViewData);
            getSupportFragmentManager().beginTransaction()
                    .add(containerLayoutId, listViewFragment)
                    .commitNow();
        }
    }

    /**
     * Saves all the items (= Entities) in the list
     * */
    protected abstract void saveEntities();

    /**
     * Gets called every time you click on a item in a list when not being in edit mode.
     *
     * @param clickedEntity clicked item (= Entity)
     * */
    public abstract void defaultOnListItemClick(I clickedEntity);
}
