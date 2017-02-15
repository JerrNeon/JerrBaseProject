package com.cw.andoridmvp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cw.andoridmvp.db.greendaogen.DaoMaster;
import com.cw.andoridmvp.db.greendaogen.DaoSession;
import com.cw.andoridmvp.db.greendaogen.MarketMainModelDao;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (greenDao管理)
 * @create by: chenwei
 * @date 2017/2/15 13:58
 */
public class GreenDaoUtil {

    private static final String db_name = "marketmainmodel-db";

    /**
     * 增删改查操作关键类
     */
    public static MarketMainModelDao getDao(Context context) {
        //初始化数据库相关
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, db_name, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        MarketMainModelDao mDao = daoSession.getMarketMainModelDao();
        return mDao;
    }

}
