package com.siddhesh.paynearbytest.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.siddhesh.paynearbytest.db.ProductDatabase;
import com.siddhesh.paynearbytest.db.ProductDetails;

import java.lang.ref.WeakReference;
import java.util.List;

public class InsertProductAsync extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "InsertProductAsync";

    private WeakReference<Context> weakReference;
    private ProductDetails productDetails;
    private ProductDatabase productDatabase;
    private TopImageAdapter dashboardFlatPagerAdapter;
    private BottomImageAdapter bottomImageAdapter;


    public InsertProductAsync(Context context, TopImageAdapter dashboardFlatPagerAdapter, BottomImageAdapter bottomImageAdapter, ProductDetails productDetails, MainActivity mainActivity1) {

        this.weakReference = new WeakReference<>(context);
        this.productDetails = productDetails;
        this.productDatabase = ProductDatabase.getInstance(context);
        this.dashboardFlatPagerAdapter=dashboardFlatPagerAdapter;
        this.bottomImageAdapter=bottomImageAdapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        productDatabase.productDao().insertOfflineData(productDetails);

        Log.d(TAG, "doInBackground: ");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        List<ProductDetails> offlineDataList = ProductDatabase.getInstance(weakReference.get()).productDao().getLastInsertedRecord();
        ProductDetails productDetails= offlineDataList.get(0);

        if(productDetails.getProductType().equals("top")){

            dashboardFlatPagerAdapter.setDataList(productDetails);
        }else {
            bottomImageAdapter.setDataList(productDetails);

        }



    }
}
