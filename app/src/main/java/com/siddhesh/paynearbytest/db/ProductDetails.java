package com.siddhesh.paynearbytest.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

@Entity(tableName = "productDetails")
public class ProductDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "productType")
    private String productType;

    @ColumnInfo(name = "productUri")
    private String productUri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductUri() {
        return productUri;
    }

    public void setProductUri(String productUri) {
        this.productUri = productUri;
    }
}
