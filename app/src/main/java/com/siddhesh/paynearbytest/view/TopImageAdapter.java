package com.siddhesh.paynearbytest.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhesh.paynearbytest.R;
import com.siddhesh.paynearbytest.db.ProductDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopImageAdapter extends PagerAdapter{
    private Context context;
    private ArrayList<ProductDetails> uriArrayList= new ArrayList<>();
    private LayoutInflater layoutInflater;


    public TopImageAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uriArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image_layout, container, false);

        ProductDetails productDetails= uriArrayList.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.topBottomImage);
        imageView.setImageURI(Uri.parse(productDetails.getProductUri())) ;

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void setDataList(ProductDetails imgUri) {

        uriArrayList.add(imgUri);
        notifyDataSetChanged();
    }

    public ArrayList<ProductDetails> getDataList() {
        return uriArrayList;
    }
}