package com.cw.jerrbase.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.cw.jerrbase.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (图片工具类)
 * @create by: chenwei
 * @date 2016/7/26 9:34
 */
public class ImageUtil {

    /**
     * 压缩图片的临时存放路径
     */
    public static final String tempPath = getImageCacheFile().getAbsolutePath() + "/" + "temp.png";

    /**
     * 应用缓存根目录
     */
    private static final String APP_CACHE_DIR = "cache";
    /**
     * 图片缓存目录
     */
    private static final String IMAGE_CACHE_DIR = "image";
    /**
     * 文件缓存目录
     */
    private static final String FILE_CACHE_DIR = "file";
    /**
     * 异常信息目录
     */
    private static final String CRASH_CACHE_DIR = "crash";

    /**
     * 获取图片缓存路径
     *
     * @param imageName 图片名
     * @return 图片路径
     */
    public static String getImageCachPath(String imageName) {
        return getImageCacheFile().getAbsolutePath() + "/" + imageName + ".png";
    }

    /**
     * 获取异常信息路径
     *
     * @param logName 文件名
     * @return log文件
     */
    public static String getCrashPath(String logName) {
        return getCrashFile().getAbsolutePath() + "/crash" + logName + ".txt";
    }

    /**
     * 获取图片缓存目录
     *
     * @return File
     */
    public static File getImageCacheFile() {
        File result = null;
        if (getCacheRootFile() != null) {
            result = new File(
                    getCacheRootFile().getAbsoluteFile() + "/" + APP_CACHE_DIR + "/" + IMAGE_CACHE_DIR);
        } else {
            result = BaseApplication.getInstance().getFilesDir();
        }
        if (!result.exists())
            result.mkdirs();
        return result;
    }

    /**
     * 获取文件缓存目录
     *
     * @return File
     */
    public static File getFileCacheFile() {
        File result = null;
        if (getCacheRootFile() != null) {
            result = new File(
                    getCacheRootFile().getAbsoluteFile() + "/" + APP_CACHE_DIR + "/" + FILE_CACHE_DIR);
        } else {
            result = BaseApplication.getInstance().getFilesDir();
        }
        if (!result.exists())
            result.mkdirs();
        return result;
    }

    /**
     * 获取异常信息目录
     *
     * @return File
     */
    public static File getCrashFile() {
        File result = null;
        if (getCacheRootFile() != null) {
            result = new File(
                    getCacheRootFile().getAbsoluteFile() + "/" + APP_CACHE_DIR + "/" + CRASH_CACHE_DIR);
        } else {
            result = BaseApplication.getInstance().getFilesDir();
        }
        if (!result.exists())
            result.mkdirs();
        return result;
    }

    /**
     * 获取缓存根目录 有SDcard放在/mnt/sdcard/Android目录下
     * <p/>
     * 没有sdcard 放在 data/data/packagename/file 目录下
     *
     * @return
     */
    public static File getCacheRootFile() {
        File result = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// SD卡已经挂载
            result = BaseApplication.getInstance().getExternalFilesDir(null);
        } else {
            result = BaseApplication.getInstance().getFilesDir();
        }
        return result;
    }

    /**
     * 压缩图片
     *
     * @param filePath   原图片路径
     * @param targetPath 目标图片路径
     * @param quality    质量
     * @return
     */
    public static String compressImage(String filePath, String targetPath, int quality) {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 800, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 获取照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 获取手机中uri的真实路径(选取手机相册返回的uri统一经过此方法获得图片路径)
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 清除用户上传图片时保存的临时文件
     */
    public static void clearTempImage() {
        if (getImageCacheFile().exists())
            deleteAllFiles(getImageCacheFile());
    }

    /**
     * 删除指定目录下的所有文件
     *
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 打开照相机(Activity)
     *
     * @param mContext      activity
     * @param cameraImgPath 拍照存储路径
     * @param requestCode   请求码
     */
    public static void openCameraActivity(Activity mContext, String cameraImgPath, int requestCode) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri picUri = Uri.fromFile(new File(cameraImgPath));
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        mContext.startActivityForResult(photoIntent, requestCode);
    }

    /**
     * 打开照相机(Fragment)
     *
     * @param mContext      fragment
     * @param cameraImgPath 拍照存储路径
     * @param requestCode   请求码
     */
    public static void openCameraFragment(Fragment mContext, String cameraImgPath, int requestCode) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri picUri = Uri.fromFile(new File(cameraImgPath));
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        mContext.startActivityForResult(photoIntent, requestCode);
    }

    /**
     * 打开相册(Activity)
     *
     * @param mContext    activity
     * @param requestCode 请求码
     */
    public static void openAlbumActivity(Activity mContext, int requestCode) {
        Intent iconIntent = new Intent(Intent.ACTION_GET_CONTENT);
        iconIntent.addCategory(Intent.CATEGORY_OPENABLE);
        iconIntent.setType("image/*");
        String[] mimeTypes = {"image/jpg", "image/jpeg", "image/png"};
        iconIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        mContext.startActivityForResult(iconIntent, requestCode);
    }

    /**
     * 打开相册(Fragment)
     *
     * @param mContext    fragment
     * @param requestCode 请求码
     */
    public static void openAlbumFragment(Fragment mContext, int requestCode) {
        Intent iconIntent = new Intent(Intent.ACTION_GET_CONTENT);
        iconIntent.addCategory(Intent.CATEGORY_OPENABLE);
        iconIntent.setType("image/*");
        mContext.startActivityForResult(iconIntent, requestCode);
    }
}
