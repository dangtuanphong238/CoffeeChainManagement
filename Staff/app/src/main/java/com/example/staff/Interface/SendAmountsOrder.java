package com.example.staff.Interface;

import com.example.staff.Model.MealModel;
import com.example.staff.Model.ModelCombo;

public interface SendAmountsOrder {
    void sendAmount(int times, MealModel meal, int last_amounts);

    void sendAmount(int i, ModelCombo mealmodel,int last_amounts);
}
