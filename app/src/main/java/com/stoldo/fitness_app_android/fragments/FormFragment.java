package com.stoldo.fitness_app_android.fragments;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.Tuple;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;
import com.stoldo.fitness_app_android.model.interfaces.Submitable;
import com.stoldo.fitness_app_android.shared.util.OtherUtil;

import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

public class FormFragment extends Fragment {

    //private FormViewModel mViewModel;
    private Object fieldInformations = null;
    private HashMap<String, Tuple<TextView, View>> labelWithView = new HashMap<>();
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
        //mViewModel = ViewModelProviders.of(this).get(FormViewModel.class);
        // TODO: Use the ViewModel

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
        Context context = getActivity();
        for (Field field : this.getAnotatedFields()){
            FormField formfield = field.getAnnotation(FormField.class);
            if(formfield != null){
                FormFieldType fieldType = formfield.type();
                if(fieldType != null){
                    TextView label = new TextView(context);
                    label.setText(field.getName());
                    label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    EditText view = null;
                    switch (fieldType){
                        case TEXTFIELD:
                            view = new EditText(context);
                            view.setForegroundGravity(Gravity.CENTER_VERTICAL);
                            break;
                        case NUMBERFIELD:
                            break;
                        case DROPDOWN:
                            break;
                        case TEXTAREA:
                            break;
                        case IMAGE:
                            break;
                    }
                    view.setText((String)OtherUtil.runGetter(field, this.fieldInformations));
                    labelWithView.put(field.getName(), new Tuple<>(label, view));
                }
            }
        }
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

    private Field[] getAnotatedFields(){
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
            throw new NotImplementedException("Diese Methode muss überschrieben werden.");
        }
        for (Field field : this.getAnotatedFields()) {
            String text = ((TextView)labelWithView.get(field.getName()).getValue()).getText().toString();
            OtherUtil.runSetter(field, this.fieldInformations, text);
        }

        this.submitable.onSubmit(this.fieldInformations);

        closeFragment();
    }

    public void onClickCancel(View v){
        closeFragment();
    }

    public void onClickOutSide(View v) {
        cancelCount++;
        switch (cancelCount){
            case 1:
                Toast.makeText(getActivity(), "Wenn Sie raus wollen nochmals klicken!", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "Bitte nochmals klicken!", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "Nur noch ein mal!", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "Scherz ab jetzt noch einmal", Toast.LENGTH_LONG).show();
                break;
            case 5:
                closeFragment();
                break;
        }
    }
}
