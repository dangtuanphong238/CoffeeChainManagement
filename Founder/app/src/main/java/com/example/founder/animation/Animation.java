package com.example.founder.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.service.controls.Control;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.RecyclerViewClick;

public class Animation {

    public static void animationForButton(Button btnControl, int fromXDelta, int toXdelta, int fromYDelta, int toYDelta){
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -2000);
        animate.setDuration(800);
        btnControl.startAnimation(animate);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(btnControl, "rotation", 360f);
        rotationAnimator.setDuration(800);
        ObjectAnimator translateAnimator =
                ObjectAnimator.ofFloat(btnControl, "translationY", 500f, 0);
        translateAnimator.setDuration(800);

        AnimatorSet set = new AnimatorSet();
        set.play(rotationAnimator);
        set.start();
    }

    public static void animationForImageButton(ImageButton btnControl, int fromXDelta, int toXdelta, int fromYDelta, int toYDelta){
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -2000);
        animate.setDuration(800);
        btnControl.startAnimation(animate);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(btnControl, "rotation", 360f);
        rotationAnimator.setDuration(800);
        ObjectAnimator translateAnimator =
                ObjectAnimator.ofFloat(btnControl, "translationY", 500f, 0);
        translateAnimator.setDuration(800);

        AnimatorSet set = new AnimatorSet();
        set.play(rotationAnimator);
        set.start();
    }

    public static void turnOnConsoleSuggest(RecyclerView recyclerView,ImageButton btnSwitch) {
        //Set location for relative_layout
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 825);
        //Set margin for view
        relativeLayoutParams.setMargins(0, 125, 0, 0);
        recyclerView.setLayoutParams(relativeLayoutParams);
        ObjectAnimator layoutRv =
                ObjectAnimator.ofFloat(recyclerView, "translationY", 825, 0);
        //layoutRv.setDuration(800);
        ObjectAnimator btnSwitchOff =
                ObjectAnimator.ofFloat(btnSwitch, "rotation", 0, 180f);
        btnSwitchOff.setDuration(800);
        ObjectAnimator btnSwitchOff1 =
                ObjectAnimator.ofFloat(btnSwitch, "translationY", 825, 0);
        btnSwitchOff.setDuration(800);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(layoutRv).with(btnSwitchOff1);
        animatorSet.play(layoutRv).with(btnSwitchOff);
        animatorSet.start();
    }

    public static void turnOffConsoleSuggest(RecyclerView recyclerView, ImageButton btnSwitch) {
        int modifierY = 900;
        ObjectAnimator layoutRv =
                ObjectAnimator.ofFloat(recyclerView, "translationY", -50, modifierY);
        // layoutRv.setDuration(800);
        ObjectAnimator btnSwitchOff =
                ObjectAnimator.ofFloat(btnSwitch, "rotation", 180f, 360f);
        //Hiệu ứng diễn ra trong n giây.
        btnSwitchOff.setDuration(800);

        ObjectAnimator btnSwitchOff1 =
                ObjectAnimator.ofFloat(btnSwitch, "translationY", 0, modifierY - 80);
        btnSwitchOff.setDuration(800);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(layoutRv).with(btnSwitchOff1);
        animatorSet.play(layoutRv).with(btnSwitchOff);
        animatorSet.start();
        // layoutRvConsoleSuggest.setVisibility(View.GONE);
    }
}
