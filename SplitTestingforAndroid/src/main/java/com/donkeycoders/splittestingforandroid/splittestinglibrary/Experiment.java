package com.donkeycoders.splittestingforandroid.splittestinglibrary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by denniskong on 10/9/13.
 */
public class Experiment implements Serializable {
    String nameOfExperiment;
    ArrayList<TestVariation> listOfTests = new ArrayList<TestVariation>();
    Map<String, Integer> experimentConfig = new HashMap<String, Integer>();

    public Experiment( String nameOfExperiment ){
        this.nameOfExperiment = nameOfExperiment;
    }

    public String getName(){
        return nameOfExperiment;
    }

    public ArrayList<TestVariation> getListOfTests(){
        return listOfTests;
    }

    public boolean addVariation(TestVariation testVariation, int chanceOfAppearanceInPercentage){
        experimentConfig.put(Character.toString(testVariation.getLabel()), chanceOfAppearanceInPercentage);
        int percentageAllocated=0;
        for (Map.Entry<String, Integer> entry : experimentConfig.entrySet())
        {
            percentageAllocated += entry.getValue();
        }
        assert percentageAllocated <= 100;
        return listOfTests.add(testVariation);
    }

    public char initialize(){
        assertChancesOfEachTestVariationAddsTo100Percent();
        return weightedRandomSelection();
    }

    private char weightedRandomSelection(){
        // Compute the total weight of all items together
        double totalWeight = 0.0d;
        for (Map.Entry<String, Integer> entry : experimentConfig.entrySet())
        {
            totalWeight += (double) entry.getValue() / 100.0;
        }
        // Now choose a random item
        double random = generateRandomDouble() * totalWeight;
        char selectedTestVariation = '\0';
        for (Map.Entry<String, Integer> entry : experimentConfig.entrySet())
        {
            random -= (double) entry.getValue() / 100.0;
            if (random <= 0.0d)
            {
                selectedTestVariation = entry.getKey().charAt(0);
                break;
            }
        }
        return selectedTestVariation;
    }

    private void assertChancesOfEachTestVariationAddsTo100Percent(){
        int percentageAllocated=0;
        for (Map.Entry<String, Integer> entry : experimentConfig.entrySet())
        {
            percentageAllocated += entry.getValue();
        }
        assert percentageAllocated == 100;
    }

    private double generateRandomDouble(){
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        return (r.nextInt(100) + 1) / 100.0;
    }
}
