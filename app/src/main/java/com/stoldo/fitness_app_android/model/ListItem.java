package com.stoldo.fitness_app_android.model;

import androidx.annotation.LayoutRes;

public interface ListItem {
    public String getTitle();
    public String getDescription();
    public String getExtra();
    public  @LayoutRes int getItemLayout();
}
