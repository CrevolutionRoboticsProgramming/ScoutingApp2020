package com.example.kyles.a2020skeletonapp;

import android.content.Context;
import android.os.AsyncTask;

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    Context ctx;
    BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
