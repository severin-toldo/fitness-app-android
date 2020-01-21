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
import com.stoldo.fitness_app_android.util.OtherUtil;

import java.util.List;

import lombok.Setter;

/**
 * @param <A> Activity
 * @param <I> Entity
 * @param <S> Service
 */
public abstract class BaseListViewActivity<A extends Subscriber<ActionEvent>, I extends ListItem & Entity, S extends AbstractSyncService> extends BaseActivity implements Subscriber<ActionEvent>, Submitable {

    @Setter
    private Class<I> entityClass;

    @Setter
    private int itemLayoutId;
    @Setter
    private int containerLayoutId;

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

    protected void initializeBaseAttributes(Class<I> entityClass, Class<S> serviceType, int itemLayoutId, int containerLayoutId){
        this.entityClass = entityClass;
        this.setServiceType(serviceType);
        this.itemLayoutId = itemLayoutId;
        this.containerLayoutId = containerLayoutId;
    }

    protected void setServiceType(Class<S> serviceType){
        if(serviceType != null){
            service = (S) OtherUtil.getSingletonInstance(serviceType);
        }
    }

    protected void setUpEditForm(I entity ) {
        FormFragment formFragment = FormFragment.newInstance(entity);
        formFragment.setSubmitable(this::onSubmit);
        getSupportFragmentManager().beginTransaction()
                .add(containerLayoutId, formFragment)
                .commitNow();
    }

    protected void setUpEditForm(){
        try {
            setUpEditForm(entityClass.newInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
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

    protected abstract void saveEntities();

    public abstract void defaultOnListItemClick(I clickedEntity);

    public void editOnListItemClick(I clickedEntity){
        setUpEditForm(clickedEntity);
    }
}
