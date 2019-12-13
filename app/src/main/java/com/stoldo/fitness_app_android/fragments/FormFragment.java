package com.stoldo.fitness_app_android.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.annotaions.FormField;
import com.stoldo.fitness_app_android.model.enums.FormFieldType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormFragment extends Fragment {

    private FormViewModel mViewModel;
    private HashMap<TextView, View> labelWithView;

    public static FormFragment newInstance(Object fieldinformations) {

        return new FormFragment(fieldinformations);
    }

    public FormFragment(Object fieldinformations){
        Field[] fields = fieldinformations.getClass().getFields();
        for (Field field : fields) {
            FormField formfield = field.getAnnotation(FormField.class);
            FormFieldType fieldType = formfield.type();
            TextView label = new TextView(getContext());
            label.setText(field.getName());
            View view = null;
            switch (fieldType){
                case TEXTFIELD:
                    view = new EditText(getContext());
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
            labelWithView.put(label, view);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FormViewModel.class);
        // TODO: Use the ViewModel

        ConstraintLayout constraintLayout = getView().findViewById(R.id.constraintLayout);

        int heigth = 25;
        for (Map.Entry<TextView, View> labelAndView : labelWithView.entrySet()){
            //ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(20, 25);
            constraintLayout.addView(labelAndView.getKey());
            constraintLayout.addView(labelAndView.getValue());
        }
        labelWithView.forEach((label, view) -> {constraintLayout.addView(label); constraintLayout.addView(view);});
    }
}
