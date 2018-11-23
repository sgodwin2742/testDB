package com.clas.testdb2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Exercise.class}, version = 1,exportSchema = false)
public abstract class ExerciseDB extends RoomDatabase {

    public abstract DaoAccess dao();

    private static ExerciseDB INSTANCE;

    public synchronized static ExerciseDB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = buildDb(context);
        }
        return INSTANCE;
    }

    private static ExerciseDB buildDb(final Context context){
        return Room.databaseBuilder(context,ExerciseDB.class,"exercise_database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d("DB_BUILD","DB onCreate() callback");
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                //getInstance(context).dao().insertExercises(Exercise.load(context));
                                List<Exercise> ex = new ArrayList<>();
                                ex.add(new Exercise("TestURL","TestName"));
                                try{
                                    Resources r = context.getResources();
                                    InputStream is = r.openRawResource(R.raw.back);
                                    int size = is.available();
                                    byte[] buffer = new byte[size];
                                    is.read(buffer);
                                    is.close();
                                    JSONArray jsData = new JSONArray(new String(buffer, "UTF-8"));
                                    for (int i=0;i<jsData.length();i++) {
                                        JSONObject ob = jsData.getJSONObject(i);
                                        ex.add(new Exercise(ob.getString("url"),ob.getString("name")));
                                    }

                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Log.e("DB_DATA_COMPILER","Failed to compile data for database");
                                } catch (IOException er) {
                                    er.printStackTrace();
                                    Log.e("DB_DATA_COMPILER","Failed to compile data for database");
                                } finally {
                                    getInstance(context).dao().insertExercises(ex);
                                }
                                //getInstance(context).dao().insertExercises(ex);
                            }
                        });
                    }
                }).build();
    }

}
