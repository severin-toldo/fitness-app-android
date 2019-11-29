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

import com.stoldo.fitness_app_android.R;

public class CardItemInformation extends Fragment {

    private CardItemInformationModel cItemInfoModel;

    private String input1;

    private CardItemInformation instance = this;

    public static CardItemInformation newInstance(String input1) {
        return new CardItemInformation(input1);
    }

    public CardItemInformation(String input1) {
        this.input1 = input1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.CardItemInformationFragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cItemInfoModel = ViewModelProviders.of(this).get(CardItemInformationModel.class);

        ConstraintLayout listElementContainer = (ConstraintLayout) getView().findViewById(R.id.list_element_background);
        listElementContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().hide(instance).commit();
            }
        });
    }
}
