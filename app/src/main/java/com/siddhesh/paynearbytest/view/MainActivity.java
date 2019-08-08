package com.siddhesh.paynearbytest.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.siddhesh.paynearbytest.R;
import com.siddhesh.paynearbytest.cropper.CropImage;
import com.siddhesh.paynearbytest.db.Favorite;
import com.siddhesh.paynearbytest.db.ProductDatabase;
import com.siddhesh.paynearbytest.db.ProductDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_READ_WRITE_PERMISSION = 345;

    private Uri imgUri;
    private boolean isTopImage;
    int topId;
    int bottomId;

    private String idCombination;
    private String newCombi;
    private List<String> newCombiList = new ArrayList<>();
    private List<String> numberInString;

    TopImageAdapter dashboardFlatPagerAdapter;
    BottomImageAdapter bottomImageAdapter;

    @BindView(R.id.vpTop)
    ViewPager topPlusImageView;

    @BindView(R.id.vpBottom)
    ViewPager vpBottom;

    @BindView(R.id.likeButton)
    LikeButton likeButton;

    @BindView(R.id.suffleImageView)
    ImageView suffleImageView;


    int f = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViewPager();
        setDataFromDb();
        pageChangeListner();
        likeButtonInit();
     //   setView();


    }

    private void setView() {

        if(bottomImageAdapter.getDataList().size()>0 && dashboardFlatPagerAdapter.getDataList().size()>0){
            likeButton.setVisibility(View.VISIBLE);
            suffleImageView.setVisibility(View.VISIBLE);
        }else {
            likeButton.setVisibility(View.GONE);
            suffleImageView.setVisibility(View.GONE);
        }
    }

    private void setDataFromDb() {

        List<ProductDetails> offlineDataList = ProductDatabase.getInstance(this).productDao().getAllOfflineData();

        for (ProductDetails productDetails : offlineDataList) {

            if (productDetails.getProductType().equals("top")) {
                dashboardFlatPagerAdapter.setDataList(productDetails);

            } else {

                bottomImageAdapter.setDataList(productDetails);
            }

        }
    }

    private void likeButtonInit() {

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {


                Toast.makeText(MainActivity.this, "like", Toast.LENGTH_SHORT).show();

                idCombination = topId + "," + bottomId;

                Favorite favorite = new Favorite();
                favorite.setCombinations(idCombination);
                new InsertFavoriteAsync(MainActivity.this, favorite).execute();


            }

            @Override
            public void unLiked(LikeButton likeButton) {


                idCombination = topId + "," + bottomId;
                ProductDatabase.getInstance(MainActivity.this).productDao().deleteFavorite(idCombination);

            }
        });
    }

    private void pageChangeListner() {

        topPlusImageView.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                ArrayList<ProductDetails> productDetailsArrayList = dashboardFlatPagerAdapter.getDataList();
                ProductDetails productDetails = productDetailsArrayList.get(position);
                topId = productDetails.getId();

                checkForFavorite();

            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.

            }
        });

        vpBottom.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArrayList<ProductDetails> productDetailsArrayList = bottomImageAdapter.getDataList();
                ProductDetails productDetails = productDetailsArrayList.get(position);
                bottomId = productDetails.getId();

                checkForFavorite();

            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.

            }
        });
    }

    private void checkForFavorite() {

        List<Favorite> favoriteList = ProductDatabase.getInstance(this).productDao().getAllFavoriteData();

        boolean isLike = false;
        for (Favorite favorite : favoriteList) {

            if (favorite.getCombinations().equals(topId + "," + bottomId)) {

                isLike = true;
                break;
            }

        }
        likeButton.setLiked(isLike);
    }

    private void initViewPager() {

        dashboardFlatPagerAdapter = new TopImageAdapter(this);
        bottomImageAdapter = new BottomImageAdapter(this);
        topPlusImageView.setAdapter(dashboardFlatPagerAdapter);
        vpBottom.setAdapter(bottomImageAdapter);
    }

    @OnClick(R.id.topPlusImageView)
    public void addTopImage() {

        isTopImage = true;
        Log.d(TAG, "addTopImage: ");
        if (isReadWritePermissionGranted()) {
            ImageUtils.launchCropActivity(MainActivity.this, true);
        } else {
            PermissionUtil.requestPermissions(MainActivity.this
                    , REQUEST_READ_WRITE_PERMISSION
                    , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @OnClick(R.id.bottomPlusImageView)
    public void addBottomImage() {
        isTopImage = false;
        if (isReadWritePermissionGranted()) {
            ImageUtils.launchCropActivity(MainActivity.this, true);
        } else {
            PermissionUtil.requestPermissions(MainActivity.this
                    , REQUEST_READ_WRITE_PERMISSION
                    , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean isReadWritePermissionGranted() {
        return PermissionUtil.checkPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                ImageUtils.launchCropActivity(MainActivity.this, true);
            }
        } else {
            List<Fragment> fragmentArrayList = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragmentArrayList) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: URI : " + result.getUri());
                imgUri = result.getUri();

                ProductDetails productDetails = new ProductDetails();
                productDetails.setProductUri(imgUri.toString());
                if (isTopImage) {

                    productDetails.setProductType("top");
                    new InsertProductAsync(MainActivity.this, dashboardFlatPagerAdapter, bottomImageAdapter, productDetails, this).execute();

                } else {

                    productDetails.setProductType("bottom");
                    new InsertProductAsync(MainActivity.this, dashboardFlatPagerAdapter, bottomImageAdapter, productDetails, this).execute();

                }


            }
        }
    }

    private void suffle() {


        List<Favorite> favoriteList = ProductDatabase.getInstance(this).productDao().getAllFavoriteData();
        List<ProductDetails> offlineDataList = ProductDatabase.getInstance(this).productDao().getAllOfflineData();
        boolean isMatched = false;

        search:
        {
            for (ProductDetails productDetails : offlineDataList) {

                if (productDetails.getProductType().equals("top")) {


                    for (ProductDetails productDetails2 : offlineDataList) {

                        if (productDetails2.getProductType().equals("bottom")) {

                            String combi = productDetails.getId() + "," + productDetails2.getId();

                            for (Favorite favorite : favoriteList) {

                                if (!favorite.getCombinations().equals(combi)) {

                                    isMatched = false;

                                } else {
                                    isMatched = true;
                                    break;
                                }
                            }

                            if (!isMatched) {
                                newCombi = combi;
                                newCombiList.add(newCombi);
                            }

                        }

                    }
                }

            }
        }

        if(newCombiList!=null) {

            if (!TextUtils.isEmpty(newCombiList.get(0))) {

                List<String> numberInString = Arrays.asList(newCombiList.get(0).split(","));
                int top = Integer.parseInt(numberInString.get(0));
                int bottom = Integer.parseInt(numberInString.get(1));

                ArrayList<ProductDetails> arrayListTop = dashboardFlatPagerAdapter.getDataList();

                for (int i = 0; i < arrayListTop.size(); i++) {

                    ProductDetails productDetails = arrayListTop.get(i);

                    if (productDetails.getId() == top) {
                        topPlusImageView.setCurrentItem(i);
                        break;
                    }

                }

                ArrayList<ProductDetails> arrayList = bottomImageAdapter.getDataList();

                for (int i = 0; i < arrayList.size(); i++) {

                    ProductDetails productDetails = arrayList.get(i);

                    if (productDetails.getId() == bottom) {
                        vpBottom.setCurrentItem(i);
                        break;
                    }

                }
            }
        }
    }

    @OnClick(R.id.suffleImageView)
    public void suffleImages() {

        if(newCombiList!=null) {

            if (newCombiList.size() > 0) {

                try {

                    numberInString = Arrays.asList(newCombiList.get(f).split(","));
                    int top = Integer.parseInt(numberInString.get(0));
                    int bottom = Integer.parseInt(numberInString.get(1));

                    ArrayList<ProductDetails> arrayListTop = dashboardFlatPagerAdapter.getDataList();

                    for (int i = 0; i < arrayListTop.size(); i++) {

                        ProductDetails productDetails = arrayListTop.get(i);

                        if (productDetails.getId() == top) {
                            topPlusImageView.setCurrentItem(i);
                            break;
                        }

                    }
                    ArrayList<ProductDetails> arrayList = bottomImageAdapter.getDataList();

                    for (int i = 0; i < arrayList.size(); i++) {

                        ProductDetails productDetails = arrayList.get(i);

                        if (productDetails.getId() == bottom) {
                            vpBottom.setCurrentItem(i);
                            break;
                        }

                    }
                    f = f + 1;


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }
        else{

            suffle();
        }

    }

}
