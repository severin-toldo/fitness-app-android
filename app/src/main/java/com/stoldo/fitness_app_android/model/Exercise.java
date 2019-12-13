package com.stoldo.fitness_app_android.model;

import androidx.annotation.LayoutRes;

import com.stoldo.fitness_app_android.R;
import com.stoldo.fitness_app_android.model.interfaces.ListItem;

import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Exercise implements ListItem {
    // TODO prepare + break item
    private Integer id;
    private String title;
    private Integer seconds;
    private String description;
    private String note;
    private String position;
    private String level; // weight or niveu, ex. 20kg, Niveau 5
    private List<String> imagePaths; // TODO make sure to support also gif format

    @Override
    public String getExtra() {
        return seconds.toString();
    }

//    @Override
//    public @LayoutRes int getItemLayout() {
//        return R.layout.exercise_item;
//    }
}
