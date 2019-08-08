package com.siddhesh.paynearbytest.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.siddhesh.paynearbytest.db.Favorite;
import com.siddhesh.paynearbytest.db.ProductDatabase;
import com.siddhesh.paynearbytest.db.ProductDetails;

import java.lang.ref.WeakReference;
import java.util.List;

public class InsertFavoriteAsync extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "InsertProductAsync";

    private WeakReference<Context> weakReference;
    private Favorite favorite;
    private ProductDatabase productDatabase;


    public InsertFavoriteAsync(Context context , Favorite favorite) {

        this.weakReference = new WeakReference<>(context);
        this.favorite = favorite;
        this.productDatabase = ProductDatabase.getInstance(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        productDatabase.productDao().insertFavorite(favorite);

        Log.d(TAG, "doInBackground: ");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }
}
