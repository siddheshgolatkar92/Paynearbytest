package com.siddhesh.paynearbytest.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.siddhesh.paynearbytest.cropper.CropImage;
import com.siddhesh.paynearbytest.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by INSODROID2 on 15-03-2018.
 */

public class ImageUtils {
    private static final String TAG = "ImageUtils";

/*
    public static String saveImage(Context context, Uri uri, String brokerID, String number) {
        Log.d(TAG, "saveImage() called with: context = [" + context + "], uri = [" + uri
                + "], brokerID = [" + brokerID + "], number = [" + number + "]");

//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        String path = context.getCacheDir().getAbsolutePath();

        File dir = new File(path, "BrokerApp");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            InputStream in = context.getContentResolver().openInputStream(uri);

            File imageFile = new File(dir, "IMG_" + number + "_" + brokerID + ".jpg");
            String imagePath = imageFile.getAbsolutePath();

            OutputStream out = new FileOutputStream(imageFile);

            byte[] buf = new byte[1024];
            int len;

            if (in != null) {
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                out.close();
                in.close();

                return imagePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Uri getFileUriFromPath(String path) {
        return Uri.fromFile(new File(path));
    }

    public static void setImageGlideUri(Context context, ImageView imageView, Uri uri, @DrawableRes int placeholderImg, @DrawableRes int errorImg) {
        if (imageView != null && uri != null) {
            Glide.with(context)
                    .load(uri)
                    .apply(new RequestOptions()
                            .error(errorImg != 0 ? errorImg : R.drawable.ic_place_holder_img)
                            .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_place_holder_img)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop())
                    .into(imageView);
        }
    }

    public static void setImageGlideUrl(Context context, ImageView imageView, String url, @DrawableRes int placeholderImg, @DrawableRes int errorImg) {

        if (imageView != null) {
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions()
                                .error(errorImg != 0 ? errorImg : R.drawable.ic_place_holder_img)
                                .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_place_holder_img)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(placeholderImg)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop())
                        .into(imageView);
            }
        }
    }


    public static void setImageGlideUrl(Context context, ImageView imageView, String url, @DrawableRes int placeholderImg) {
        if (imageView != null && !TextUtils.isEmpty(url)) {

            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .error(placeholderImg != 0 ? placeholderImg : R.drawable.ic_place_holder_img)
                            .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_place_holder_img)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop())
                    .into(imageView);
        }
    }

    public static void setImageGlideUrl(Context context, ImageView imageView, String url) {
        if (imageView != null && !TextUtils.isEmpty(url)) {

            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop())
                    .into(imageView);
        }
    }

    public static void setImageGlideUrlWithoutCenterCrop(Context context, ImageView imageView, String url) {
        if (imageView != null && !TextUtils.isEmpty(url)) {

            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        }
    }

    public static void setImageGlideUrl(Context context, ImageView imageView, String url, @DrawableRes int placeholderImg, final ShimmerFrameLayout shimmerFrameLayout) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            shimmerFrameLayout.setAutoStart(true);
            Glide.with(context)
                    .load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    shimmerFrameLayout.setAutoStart(false);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    shimmerFrameLayout.setAutoStart(false);
                    return false;
                }
            }).apply(new RequestOptions()
                    .error(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default)
                    .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()).into(imageView);
        }
    }

    public static void setImageGlideUrlWithoutCenterCrop(Context context, ImageView imageView, String url, @DrawableRes int placeholderImg, final ShimmerFrameLayout shimmerFrameLayout) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            shimmerFrameLayout.setAutoStart(true);
            Glide.with(context)
                    .load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    shimmerFrameLayout.setAutoStart(false);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    shimmerFrameLayout.setAutoStart(false);
                    return false;
                }
            }).apply(new RequestOptions()
                    .error(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default)
                    .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        }
    }
*/


//    public static void setImagePicassoUri(Context context, ImageView imageView, Uri uri, @DrawableRes int placeholderImg, @DrawableRes int errorImg) {
//
//        if (imageView != null && uri != null) {
//
//            Picasso.with(context)
//                    .invalidate(uri);
//
//            Picasso.with(context)
//                    .load(uri)
//                    .fit()
//                    .centerCrop()
//                    .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default_new)
//                    .apiError(errorImg != 0 ? errorImg : R.drawable.ic_profile_default_new)
//                    .into(imageView);
//        } else {
//            Log.e(TAG, "setImagePicassoUri: IMAGEVIEW NULL");
//        }
//    }

//    public static void setImagePicassoUrl(Context context, ImageView imageView, String url, @DrawableRes int placeholderImg, @DrawableRes int errorImg) {
//
//        if (imageView != null && !url.isEmpty()) {
//
//            Picasso.with(context)
//                    .invalidate(url);
//
//            Picasso.with(context)
//                    .load(url)
//                    .fit()
//                    .centerCrop()
//                    .placeholder(placeholderImg != 0 ? placeholderImg : R.drawable.ic_profile_default_new)
//                    .apiError(errorImg != 0 ? errorImg : R.drawable.ic_profile_default_new)
//                    .into(imageView);
//        } else {
//            Log.e(TAG, "setImagePicassoUrl: IMAGEVIEW NULL");
//        }
//    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapFromUrl(String serverImgUrl) {
        try {
            URL url = new URL(serverImgUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void setBannerBg(final ImageView bannerImageView, Bitmap bitmap) {
//        if (bitmap != null) {
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                public void onGenerated(Palette palette) {
//                    final Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
//                    final Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//                    final Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
//                    if (lightVibrantSwatch != null) {
//                        bannerImageView.setBackgroundColor(lightVibrantSwatch.getRgb());
//                        return;
//                    } else if (darkVibrantSwatch != null) {
//                        bannerImageView.setBackgroundColor(darkVibrantSwatch.getRgb());
//                        return;
//                    } else if (vibrantSwatch != null) {
//                        bannerImageView.setBackgroundColor(vibrantSwatch.getRgb());
//                    }
//                }
//            });
//        }
//    }

    public static void launchCropActivity(@NonNull Activity activity, boolean isCropShapeSet) {
        if (isCropShapeSet) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityTitle("")
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setCropMenuCropButtonTitle("Crop")
                    .setFixAspectRatio(true)
                    .setRequestedSize(400, 400)
                    .start(activity);
        } else {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityTitle("")
                    .start(activity);
        }
    }
}
