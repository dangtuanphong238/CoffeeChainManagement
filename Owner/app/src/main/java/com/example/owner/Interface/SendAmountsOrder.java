package com.example.owner.Interface;


import com.example.owner.Model.MealModel;
import com.example.owner.Model.ModelCombo;

public interface SendAmountsOrder {
    void sendAmount(int times, MealModel meal, int last_amounts);
    void sendAmount(int i, ModelCombo mealmodel, int last_amounts);
}
