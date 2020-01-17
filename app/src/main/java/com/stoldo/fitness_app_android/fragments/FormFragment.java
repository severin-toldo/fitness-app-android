package com.stoldo.fitness_app_android.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.Tuple;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.shared.util.LogUtil;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

// TODO java doc --> Stefano
public class FormFragment extends Fragment {

    //private FormViewModel mViewModel;
    private Object fieldInformations = null;
    private Map<String, Tuple<TextView, View>> labelWithView = new HashMap<>();
    private int cancelCount = 0;

    @Setter
    private Submitable submitable = null;

    public static FormFragment newInstance(Object fieldinformations) {
        return new FormFragment(fieldinformations);
}

    public FormFragment(Object fieldinformations){
        this.fieldInformations = fieldinformations;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.CreateViews();

        LinearLayout linearLayout = getView().findViewById(R.id.genericLayout);
        LayoutParams labelParams = getLabelParams();
        LayoutParams viewParams = getViewParams();

        for (Map.Entry<String, Tuple<TextView, View>> nameView : labelWithView.entrySet()){
            Tuple<TextView, View> labelAndView = nameView.getValue();
            TextView label = labelAndView.getKey();
            View view = labelAndView.getValue();
            linearLayout.addView(label, labelParams);
            linearLayout.addView(view, viewParams);
        }

        linearLayout.addView(createSubmitAndCancelButton());

        linearLayout.bringToFront();
        ConstraintLayout ll = getView().findViewById(R.id.constraintLayout);
        ll.setOnClickListener(this::onClickOutSide);
    }

    private void CreateViews() {
        HashMap<String, Tuple<TextView, View>> tempMap = new HashMap<>();
        Context context = getActivity();
        for (Field field : this.getFormFields()){
            FormField formfield = field.getAnnotation(FormField.class);
            if(formfield != null){
                FormFieldType fieldType = formfield.type();
                int index = formfield.index();
                if(fieldType != null){
                    TextView label = new TextView(context);
                    // TODO get label text view getRessource() from annotation --> Stefano
                    String labelValue = getResources().getString(formfield.labelResRef());
                    label.setText(labelValue);
                    label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    if(!OtherUtil.canBeNull(field)){
                        label.setTypeface(label.getTypeface(), Typeface.BOLD);
                    }
                    EditText view = null;
                    switch (fieldType){
                        case TEXTFIELD:
                            view = new EditText(context);
                            view.setForegroundGravity(Gravity.CENTER_VERTICAL);
                            break;
                        case NUMBERFIELD:
                            // TODO make number --> Stefano
                            view = new EditText(context);
                            view.setForegroundGravity(Gravity.CENTER_VERTICAL);
                            break;
                        case TEXTAREA:
                            // TODO make textarea --> Stefano
                            view = new EditText(context);
                            view.setForegroundGravity(Gravity.CENTER_VERTICAL);
                            break;
                    }

                    Object fieldValue = OtherUtil.runGetter(field, this.fieldInformations);
                    if(fieldValue != null) {
                        view.setText(fieldValue.toString());
                    }

                    tempMap.put(field.getName(), new Tuple<>(label, view));
                    Tuple labelAndView = tempMap.get(field.getName());
                    labelAndView.setExtra("index", index);
                }
            }
        }

        labelWithView = sortHashMapByValues(tempMap);
    }

    public <K extends Comparable, V extends Comparator> LinkedHashMap<K, V> sortHashMapByValues(HashMap<K, V> passedMap) {
        List<K> mapKeys = new ArrayList<>(passedMap.keySet());
        List<V> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues, (o1, o2) -> o1.compare(o1, o2));
        Collections.sort(mapKeys, (o1, o2) -> o1.compareTo(o2));

        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();

        Iterator<V> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            V val = valueIt.next();
            Iterator<K> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                K key = keyIt.next();
                V comp1 = passedMap.get(key);
                V comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    private LinearLayout createSubmitAndCancelButton(){
        LinearLayout horizontalLinearlayout = new LinearLayout(getActivity());
        horizontalLinearlayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        horizontalLinearlayout.setLayoutParams(layoutParams);

        Button buttonApply = new Button(getActivity());
        buttonApply.setText("Apply");
        buttonApply.setOnClickListener(this::onSubmit);
        Button buttonCancel = new Button(getActivity());
        buttonCancel.setOnClickListener(this::onClickCancel);
        buttonCancel.setText("Cancel");

        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams1.weight = 1;
        horizontalLinearlayout.addView(buttonCancel, layoutParams1);
        horizontalLinearlayout.addView(buttonApply, layoutParams1);

        return horizontalLinearlayout;
    }

    private LayoutParams getLabelParams(){
        LayoutParams labelParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int marginStart = OtherUtil.convertDpToPixel(10, getActivity());
        int marginEnd = marginStart;
        int marginTop = OtherUtil.convertDpToPixel(15, getActivity());
        labelParams.setMarginStart(marginStart);
        labelParams.setMarginEnd(marginEnd);
        labelParams.topMargin = marginTop;

        return labelParams;
    }

    private LayoutParams getViewParams(){
        LayoutParams viewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int marginStart = OtherUtil.convertDpToPixel(10, getActivity());
        int marginEnd = marginStart;
        int marginBottom = OtherUtil.convertDpToPixel(25, getActivity());
        viewParams.setMarginStart(marginStart);
        viewParams.setMarginEnd(marginEnd);
        viewParams.bottomMargin = marginBottom;

        return viewParams;
    }

    public void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private Field[] getFormFields(){
        ArrayList<Field> annotatedFields = new ArrayList<>();

        Field[] fields = this.fieldInformations.getClass().getDeclaredFields();
        Context context = getActivity();
        for (Field field : fields) {
            FormField formfield = field.getAnnotation(FormField.class);
            if(formfield != null){
                annotatedFields.add(field);
            }
        }

        Field[] arrayField = new Field[annotatedFields.size()];
        annotatedFields.toArray(arrayField);
        return arrayField;
    }

    public void onSubmit(View v) {
        if(this.submitable == null){
            LogUtil.logError("Die Methode onSubmit muss überschrienen werden mit setSubmitable" , this.getClass(), new NotImplementedException("Diese Methode muss überschrieben werden."));
            throw new NotImplementedException("Diese Methode muss überschrieben werden.");
        }

        boolean succeded = true;
        for (Field field : this.getFormFields()) {

            Tuple<TextView, View> labelAndView = labelWithView.get(field.getName());
            if(labelAndView != null) {
                //value = editview, key = label
                TextView view = ((TextView)labelAndView.getValue());
                String text = view.getText().toString();
                if(!OtherUtil.canBeNull(field)){
                    if(text == null || text.isEmpty()){
                        view.setError("Dieses Feld ist Pflicht");
                        succeded = false;
                        continue;
                    }
                }
                OtherUtil.runSetter(field, this.fieldInformations, text);
            }
        }

        if(succeded){
            this.submitable.onSubmit(this.fieldInformations);
            closeFragment();
        }

        OtherUtil.hideKeyboard(this.getActivity());
    }

    public void onClickCancel(View v){
        closeFragment();
    }

    public void onClickOutSide(View v) {
        closeFragment();
    }
}
