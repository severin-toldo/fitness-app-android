package com.stoldo.fitness_app_android.fragments;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.stoldo.fitness_app_android.R;

import java.util.ArrayList;
import java.util.List;

public class CardViewFragment2 extends Fragment {

    private CardViewModel2 cViewModel;

    private String input1;

    public static CardViewFragment2 newInstance(String input1) {
        return new CardViewFragment2(input1);
    }

    public CardViewFragment2(String input1) {
        this.input1 = input1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_activity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cViewModel = ViewModelProviders.of(this).get(CardViewModel2.class);

//        List<CardItemInformation2> frags = new ArrayList<>();
//
//        for (int i = 0; i < 20; i++) {
//            CardItemInformation2 frag = CardItemInformation2.newInstance("Fragment " + i);
//            frags.add(frag);
//        }
//
//        ArrayAdapter<CardItemInformation2> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, frags);
//        CardView cardView = (CardView) getView().findViewById(R.id.cardViewList);
//        //cardView.setAdapter(adapter);
//        cardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CardItemInformation2 frag = CardItemInformation2.newInstance("New Frag " + position);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.cardViewListContainer, frag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
    }
}
