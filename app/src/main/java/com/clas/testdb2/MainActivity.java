package com.clas.testdb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ExerciseDB exerciseDb;
    private static final String DATABASE_NAME = "exercise_database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        Button bt = (Button) findViewById(R.id.button);

        /*
        exerciseDb =Room.databaseBuilder(
                getApplicationContext(),ExerciseDB.class,DATABASE_NAME)
                .fallbackToDestructiveMigration().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Exercise> ex = new ArrayList<>();
                ex.add(new Exercise("CpWsUsqBtN8","Deficit Deadlift"));
                ex.add(new Exercise("lMBKFUfJw9o","Reverse Grip Bent-Over Rows"));
                ex.add(new Exercise("iUNoLR0pYjY","Pull Up"));
                exerciseDb.dao().insertExercises(ex);
            }
        }).start();
        */

        exerciseDb = ExerciseDB.getInstance(getApplicationContext());

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB();
            }
        });

        do {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ExerciseDBObject> exerciseList;

                        exerciseList = exerciseDb.dao().fetchAllExercises();
                        if (exerciseList.size() == 0) {
                            return;
                        }else{

                            for (int i = 0; i < exerciseList.size(); i++) {
                                ExerciseDBObject exercise = exerciseList.get(i);
                                Log.d("DataRetrieved", exercise.getName());
                                if (tv.getText() != "") {
                                    tv.setText(tv.getText() + "\n" + exercise.getName() + " Wanted:" + exercise.getWanted() +"\n Steps:     "+exercise.getSteps());
                                } else {
                                    tv.setText(exercise.getName());
                                }

                            }
                        }

            }}).start();
            }while (tv.getText() == "");

        //exerciseDb = ExerciseDB.getInstance(getApplicationContext());

    }

    public void updateDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ExerciseDBObject> exerciseList = exerciseDb.dao().fetchAllExercises();
                for (int i = 0; i < exerciseList.size(); i++) {
                    if (exerciseList.get(i).getWanted() == 1) {
                        exerciseList.get(i).setWanted(0);
                    } else {
                        exerciseList.get(i).setWanted(1);
                    }
                }
                exerciseDb.dao().updateExercises(exerciseList);
            }
        }).start();
    }
}
