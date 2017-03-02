package com.cw.jerrbase.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/19 15:10
 */
public class MyContentProvider extends ContentProvider {

    private static final int INCOMING_COLLECTION = 1;
    private static final int INCOMING_SINGLE = 2;

    public static final String autors = "com.ourslook.mvp.personprovider";
    private static final String PERSONS_PATH = "person";
    private static final String PERSON_PATH = "person/#";

    public static String content_persons = String.format("content://%s/%s", autors, PERSONS_PATH);
    public static String content_person = String.format("content://%s/%s", autors, PERSON_PATH);

    // 数据集的MIME类型字符串则应该以vnd.android.cursor.dir/开头
    private static final String CONTENT_TYPE = "vnd.android.cursor.dir";
    // 单一数据的MIME类型字符串应该以vnd.android.cursor.item/开头
    private static final String CONTENT_TYPE_ITME = "vnd.android.cursor.item";

    private static final String DATABASE_NAME = "person_db";
    private static final String TABLE_NAME = "person";
    private static final String TABLE_COLUMN_ID = "_id";
    private static final String TABLE_COLUMN_NAME = "name";

    // DatabaseHelper操作句柄
    private DBHelper dbHelper;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(autors, PERSONS_PATH, INCOMING_COLLECTION);
        uriMatcher.addURI(autors, PERSON_PATH, INCOMING_SINGLE);//#号为通配符
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(this.getContext(), DATABASE_NAME, null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case INCOMING_COLLECTION:
                return sqLiteDatabase.query(TABLE_NAME, strings, s, strings1, null, null, null);
            case INCOMING_SINGLE:
                long id = ContentUris.parseId(uri);
                String where = TABLE_COLUMN_ID + "=" + id;
                if (s != null && !"".equals(s))
                    s = where + "and" + s;
                return sqLiteDatabase.query(TABLE_NAME, strings, s, strings1, null, null, null);
            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME, TABLE_COLUMN_NAME, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case INCOMING_COLLECTION:
                return sqLiteDatabase.delete(TABLE_NAME, s, strings);
            case INCOMING_SINGLE:
                long id = ContentUris.parseId(uri);
                String where = TABLE_COLUMN_ID + "=" + id;
                if (s != null && !"".equals(s))
                    s = where + "and" + s;
                return sqLiteDatabase.delete(TABLE_NAME, s, strings);
            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case INCOMING_COLLECTION:
                return sqLiteDatabase.update(TABLE_NAME, contentValues, s, strings);
            case INCOMING_SINGLE:
                long id = ContentUris.parseId(uri);
                String where = TABLE_COLUMN_ID + "=" + id;
                if (s != null && !"".equals(s))
                    s = where + "and" + s;
                return sqLiteDatabase.update(TABLE_NAME, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("UnKnown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case INCOMING_COLLECTION:
                return CONTENT_TYPE;
            case INCOMING_SINGLE:
                return CONTENT_TYPE_ITME;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
}
