package com.cw.andoridmvp.base.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Glide配置)
 * @create by: chenwei
 * @date 2016/10/9 10:14
 */
public class GlideModelConfig implements GlideModule {

    private int diskSize = 1024 * 1024 * 100;
    private int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义缓存大小和位置
        //builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //内存中
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); //sd卡中

        // 默认内存和图片池大小
        //MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        //int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
        //int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
        //builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
        //builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));

        // 自定义内存和图片池大小
        //builder.setMemoryCache(new LruResourceCache(memorySize));
        //builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        //builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
