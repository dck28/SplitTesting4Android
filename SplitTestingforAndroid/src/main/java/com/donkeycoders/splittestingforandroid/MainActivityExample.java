package com.donkeycoders.splittestingforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.donkeycoders.splittestingforandroid.splittestinglibrary.Experiment;
import com.donkeycoders.splittestingforandroid.splittestinglibrary.SplitTest;
import com.donkeycoders.splittestingforandroid.splittestinglibrary.TestVariation;

public class MainActivityExample extends Activity implements SplitTest.Interface {

    SplitTest splitTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_example);

        splitTest = new SplitTest(this);
        final Experiment experiment1 = new Experiment("Experiment #1");
        experiment1.addVariation(new TestVariation('A'), 50); // indicate chance of showing this test with int between 0 and 100
        experiment1.addVariation(new TestVariation('B'), 50); // indicate chance of showing this test with int between 0 and 100
        splitTest.add(experiment1);

        char variation = splitTest.getOrInitializeTestVariationOf(experiment1); // this will check if the sum of all test variations' chances add ups to exactly 100
        switch (variation){
            case 'A':
                // Apply TestVariation A
                visited(experiment1);
                break;
            case 'B':
                // Apply TestVariation B
                visited(experiment1);
                break;
        }

        TextView show_current_test = (TextView)findViewById(R.id.show_test);
        show_current_test.setText("Test Variation: " + Character.toString(variation));

        Button goal_reached_button = (Button)findViewById(R.id.goal_reached);
        goal_reached_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inform the user the button has been clicked
                goalReached(experiment1);
            }
        });

    }

    @Override
    public void visited(Experiment experiment) {
        // Your implementations as needed when the test is visited
        // ...
        Toast.makeText(this, experiment.getName() + " ran with Test Variation: " + splitTest.getOrInitializeTestVariationOf(experiment),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void goalReached(Experiment experiment) {
        // Your implementations as needed when the test's goal is reached
        // ...
        Toast.makeText(this, "Goal reached by Test Variation: " + splitTest.getOrInitializeTestVariationOf(experiment) + " for " + experiment.getName(),
                Toast.LENGTH_LONG).show();
    }
}
