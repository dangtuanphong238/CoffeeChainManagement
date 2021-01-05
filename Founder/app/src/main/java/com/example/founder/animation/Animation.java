package com.example.founder.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.service.controls.Control;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class Animation {

    public void ani(Button btnControl, int fromXDelta, int toXdelta){
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
}
