package com.cw.jerrbase.base.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (使用于Fragment)
 * @create by: chenwei
 * @date 2017/3/15 9:45
 */
public interface IRoute4 extends IRoute3 {

    /**
     * 获得Fragment对象
     *
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    <T extends Fragment> T newInstance(@NonNull Class<T> tClass);

    /**
     * 获得Fragment对象并传递参数
     *
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    <T extends Fragment> T newInstance(@NonNull Class<T> tClass,@NonNull int param);

    /**
     * 获得Fragment对象并传递参数
     *
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    <T extends Fragment> T newInstance(@NonNull Class<T> tClass,@NonNull long param);

    /**
     * 获得Fragment对象并传递参数
     *
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    <T extends Fragment> T newInstance(@NonNull Class<T> tClass,@NonNull String param);

    /**
     * 获得Fragment对象并传递参数
     *
     * @param params 要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    <T extends Fragment> T newInstance(@NonNull Class<T> tClass,@NonNull Bundle params);
}
