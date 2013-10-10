package com.donkeycoders.splittestingforandroid.splittestinglibrary;

import java.io.Serializable;

/**
 * Created by denniskong on 10/9/13.
 */
public class TestVariation implements Serializable {

    char testLabel;
    int numVisited;
    int numGoalReached;

    public TestVariation( char testLabel ){
        this.testLabel = testLabel;
        numVisited = numGoalReached = 0;
    }

    public char getLabel(){
        return testLabel;
    }

    public int getNumVisited(){
        return numVisited;
    }

    public void visited(){
        numVisited++;
    }

    public int getNumGoalReached(){
        return numGoalReached;
    }

    public void goalReached(){
        numGoalReached++;
    }
}
