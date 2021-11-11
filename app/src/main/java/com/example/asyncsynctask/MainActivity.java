package com.example.asyncsynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
 private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    progressBar=findViewById(R.id.progress);

    }

    public void StartAsyncTask(View view) {
        exampleAsyncTask task=new exampleAsyncTask(this);
        task.execute(10);
        //
    }
    /** we get one warning This asynctask class should be static or leaks might occur*/

/**this is non static class which has implicit reference to outer class which is main activity*/
//make class static

    //or weak reference
                                                  //param,progress,type of result

private static class exampleAsyncTask extends AsyncTask<Integer,Integer,String>{
/**

 */
//if the mainactivity get destroyed and we can acess the variable by garbage collector
        //weak refernce
    private WeakReference<MainActivity>activityWeakReference;
exampleAsyncTask(MainActivity activity){
    activityWeakReference=new WeakReference<MainActivity>(activity);
}
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity=activityWeakReference.get(); //we get the strong reference
           /**
            why we need strong reference when this got memory leak??
            only in the scope scope over our reference is ready for gc


            */
           if(activity==null|| activity.isFinishing()){
               return;
           }
           activity.progressBar.setVisibility(View.VISIBLE);
        }
        /** this method is what doing in background*/
        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
publishProgress((i*100)/integers[0]);//call the onprogressUpdate
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
           return "Finished !";
        }
        /**update the progress*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity=activityWeakReference.get(); //we get the strong reference
            /**
             why we need strong reference when this got memory leak??
             only in the scope scope over our reference is ready for gc


             */
            if(activity==null|| activity.isFinishing()){
                return;
            }
       activity.progressBar.setProgress(values[0]);
        }

/** ... vargs pass as many integer as we want*/

/**get result back and post it to ui*/
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity=activityWeakReference.get(); //we get the strong reference
            /**
             why we need strong reference when this got memory leak??
             only in the scope scope over our reference is ready for gc


             */
            if(activity==null|| activity.isFinishing()){
                return;
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
       activity.progressBar.setProgress(0);
        activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
/**async task is  android class which use to do work in background thread and publish the result in ui thread
 */