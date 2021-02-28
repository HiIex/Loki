package com.example.md.loki;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class AnimatorUtil {
    private static final String TAG="AnimatorUtil";

    public static void start(Animator animator) {
        if (animator != null && !animator.isStarted()) {
            //resetDurationScale();
            animator.start();
        }
    }

    public static void stop(Animator animator) {
        if (animator != null && !animator.isRunning()) {
            animator.end();
        }
    }

    public static void start(Loki... lokis) {
        for (Loki loki : lokis) {
            loki.start();
        }
    }

    public static void start(ArrayList<Loki> lokis){
        if(lokis!=null&&lokis.size()>0){
            for(Loki loki:lokis){
                loki.start();
            }
        }
    }

    public static void stop(Loki... lokis) {
        for (Loki loki:lokis) {
            loki.stop();
        }
    }

    public static void stop(ArrayList<Loki> lokis){
        if(lokis!=null&&lokis.size()>0){
            for(Loki loki:lokis){
                loki.stop();
            }
        }
    }

    public static boolean isRunning(Loki... lokis) {
        for (Loki loki : lokis) {
            if (isRunning(loki.getAnimator())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRunning(ArrayList<Loki> lokis){
        if(lokis!=null&&lokis.size()>0){
            for(Loki loki:lokis){
                if(isRunning(loki.getAnimator())){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    public static boolean isStarted(Animator animator) {
        return animator != null && animator.isStarted();
    }

    public static boolean isStarted(ArrayList<Loki> lokis){
        if(lokis!=null&&lokis.size()>0){
            for(Loki loki:lokis){
                if(isStarted(loki.getAnimator())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重置动画缩放时长
     */
    public static void resetDurationScale() {
        try {
            getField().setFloat(null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private static Field getField() throws NoSuchFieldException {
        Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
        field.setAccessible(true);
        return field;
    }


}
