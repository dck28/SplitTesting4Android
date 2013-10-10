package com.donkeycoders.splittestingforandroid.splittestinglibrary;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by denniskong on 10/9/13.
 */
public class SplitTest extends ArrayList<Experiment> implements Serializable {

    Context context;
    SharedPreferences splitteststorage;

    public abstract interface Interface {
        public abstract void visited(Experiment experiment);
        public abstract void goalReached(Experiment experiment);
    }

    public SplitTest (Context context){
        super();
        this.context = context;
        this.splitteststorage = context.getSharedPreferences(context.getPackageName() + "_SPLIT_TEST", Context.MODE_PRIVATE);
    }

    public char getOrInitializeTestVariationOf(Experiment experiment){
        String FIRST_LAUNCH_NAMESPACE = experiment.getName() + "_FIRST_LAUNCH";
        String TEST_VARIATION_IN_PROGRESS = experiment.getName() + "_TEST_VARIATION_IN_PROGRESS";
        char testVariationInProgress;
        if (splitteststorage.getBoolean(FIRST_LAUNCH_NAMESPACE, true)){
            testVariationInProgress = experiment.initialize();
            splitteststorage.edit()
                    .putBoolean(FIRST_LAUNCH_NAMESPACE, false)
                    .putString(TEST_VARIATION_IN_PROGRESS, Character.toString(testVariationInProgress))
                    .commit();
        } else {
            String tempString = splitteststorage.getString(TEST_VARIATION_IN_PROGRESS, "");
            testVariationInProgress = tempString.charAt(0);
        }
        return testVariationInProgress;
    }

}