package com.cw.andoridmvp.base.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cw.andoridmvp.R;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Glide工具类)
 * @create by: chenwei
 * @date 2016/10/9 11:14
 */
public class GlideUtil {

    private static final int defaultErrorResourceId = R.drawable.icon_home_on;

    /*----------------------------------------    使用默认错误图片(无占位图)   -------------------------------------------*/

    /**
     * 显示图片(使用默认错误图片(无占位图))
     *
     * @param context   activity
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImage(final Activity context, final String url, final ImageView imageView) {
        Glide.with(context).load(url).error(defaultErrorResourceId).into(imageView);
    }

    /**
     * 显示图片(使用默认错误图片(无占位图))
     *
     * @param context   activity
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImage(final Fragment context, final String url, final ImageView imageView) {
        Glide.with(context).load(url).error(defaultErrorResourceId).into(imageView);
    }

    /**
     * 显示图片(使用默认错误图片(无占位图))
     *
     * @param context   activity
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImage(final Context context, final String url, final ImageView imageView) {
        Glide.with(context).load(url).error(defaultErrorResourceId).into(imageView);
    }

    /*----------------------------------------    不使用默认错误图片(无占位图)   -------------------------------------------*/

    /**
     * 显示图片(不使用默认错误图片(无占位图))
     *
     * @param context         activity
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImage(final Activity context, final String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).error(errorResourceId).into(imageView);
    }

    /**
     * 显示图片(不使用默认错误图片(无占位图))
     *
     * @param context         activity
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImage(final Fragment context, final String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).error(errorResourceId).into(imageView);
    }

    /**
     * 显示图片(不使用默认错误图片(无占位图))
     *
     * @param context         activity
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImage(final Context context, final String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).error(errorResourceId).into(imageView);
    }

    /*----------------------------------------    使用默认错误图片(有占位图)   -------------------------------------------*/

    /**
     * 显示图片(使用默认错误图片(有占位图))
     *
     * @param context               activity
     * @param url                   图片地址
     * @param imageView             imageview
     * @param placeholderResourceId 占位图图片
     */
    public static void displayImage(final Activity context, final String url, final int placeholderResourceId, final ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeholderResourceId).crossFade().error(defaultErrorResourceId).into(imageView);
    }

    /**
     * 显示图片(使用默认错误图片(有占位图))
     *
     * @param context               activity
     * @param url                   图片地址
     * @param imageView             imageview
     * @param placeholderResourceId 占位图图片
     */
    public static void displayImage(final Fragment context, final String url, final int placeholderResourceId, final ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeholderResourceId).crossFade().error(defaultErrorResourceId).into(imageView);
    }

    /**
     * 显示图片(使用默认错误图片(有占位图))
     *
     * @param context               activity
     * @param url                   图片地址
     * @param imageView             imageview
     * @param placeholderResourceId 占位图图片
     */
    public static void displayImage(final Context context, final String url, final int placeholderResourceId, final ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeholderResourceId).crossFade().error(defaultErrorResourceId).into(imageView);
    }

    /*----------------------------------------    使用默认错误图片显示圆形图片(无占位图)   -------------------------------------------*/


    /**
     * 显示圆形图片(使用默认错误图片显示圆形图片(无占位图))
     *
     * @param context   activity
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImageWithRound(final Activity context, final String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(defaultErrorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 显示圆形图片(使用默认错误图片显示圆形图片(无占位图))
     *
     * @param context   fragment
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImageWithRound(final Fragment context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(defaultErrorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 显示圆形图片(使用默认错误图片显示圆形图片(无占位图))
     *
     * @param context   context
     * @param url       图片地址
     * @param imageView imageview
     */
    public static void displayImageWithRound(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(defaultErrorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /*----------------------------------------    不使用默认错误图片显示圆形图片(无占位图)   -------------------------------------------*/


    /**
     * 显示圆形图片(不使用默认错误图片显示圆形图片(无占位图)）
     *
     * @param context         activity
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImageWithRound(final Activity context, final String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(errorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 显示圆形图片(不使用默认错误图片显示圆形图片(无占位图)）
     *
     * @param context         fragment
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImageWithRound(final Fragment context, String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(errorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 显示圆形图片(不使用默认错误图片显示圆形图片(无占位图)）
     *
     * @param context         context
     * @param url             图片地址
     * @param imageView       imageview
     * @param errorResourceId 错误图片
     */
    public static void displayImageWithRound(final Context context, String url, final ImageView imageView, final int errorResourceId) {
        Glide.with(context).load(url).asBitmap().centerCrop().error(errorResourceId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

}
