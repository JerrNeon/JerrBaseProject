package com.cw.jerrbase.util.dialog;

import android.app.Activity;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (对话框工具类)
 * @create by: chenwei
 * @date 2016/10/11 9:49
 */
public class DialogUtils {

    /**
     * 显示上传图片弹出的对话框
     *
     * @param activity
     * @param listener 每项的点击事件
     */
    public static void showImageDialog(Activity activity, OnItemClickListener listener) {
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle(null)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(new String[]{"拍照", "从相册选择"})
                .setOthers(null)
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    /**
     * 显示从底部弹出的对话框
     *
     * @param activity
     * @param listener    每项的点击事件
     * @param destructive 每项的内容
     */
    public static void showActionSheetDialog(Activity activity, OnItemClickListener listener, String... destructive) {
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle(null)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(destructive)
                .setOthers(null)
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    /**
     * 显示提示对话框
     *
     * @param activity
     * @param title       标题
     * @param listener    每项的点击事件
     * @param destructive 每项的内容
     */
    public static void showAlertDialog(Activity activity, String title, OnItemClickListener listener, String... destructive) {
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.Alert)
                .setTitle(title)
                .setMessage(null)
                .setCancelText(null)
                .setDestructive(destructive)
                .setOthers(null)
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    /**
     * 显示提示对话框(已经设置"取消"和"确定"按钮)
     *
     * @param activity
     * @param title    标题
     * @param listener 每项的点击事件
     */
    public static void showAlertDialog(Activity activity, String title, OnItemClickListener listener) {
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.Alert)
                .setTitle(title)
                .setMessage(null)
                .setCancelText(null)
                .setDestructive(new String[]{"取消"})
                .setOthers(new String[]{"确定"})
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    /**
     * 显示列表对话框
     *
     * @param activity
     * @param listener    每项的点击事件
     * @param destructive 每项的内容
     */
    public static void showAlertDialog(Activity activity, OnItemClickListener listener, String... destructive) {
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.Alert)
                .setTitle(null)
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive(destructive)
                .setOthers(null)
                .setOnItemClickListener(listener)
                .build()
                .show();
    }

    /**
     * 显示年月日时分
     *
     * @param activity
     */
    public static void showDateTimeDialog(Activity activity) {
        new TimePickerView(activity, TimePickerView.Type.ALL).show();
    }

    /**
     * 显示年月日
     *
     * @param activity
     */
    public static void showDateDialog(Activity activity) {
        new TimePickerView(activity, TimePickerView.Type.YEAR_MONTH_DAY).show();
    }

    /**
     * 显示时分
     *
     * @param activity
     */
    public static void showTimeDialog(Activity activity) {
        new TimePickerView(activity, TimePickerView.Type.HOURS_MINS).show();
    }

    /**
     * 显示三级列表
     *
     * @param activity
     * @param title         标题
     * @param options1Items 第一项列表
     * @param options2Items 第二项列表
     * @param options3Items 第三项列表
     * @param linkage       是否联动
     * @param listener      每项的点击事件
     * @return OptionsPickerView
     */
    public static OptionsPickerView showTreeLinkage(Activity activity, String title, ArrayList<?> options1Items,
                                                    ArrayList<ArrayList<?>> options2Items,
                                                    ArrayList<ArrayList<ArrayList<?>>> options3Items,
                                                    boolean linkage, OptionsPickerView.OnOptionsSelectListener listener) {
        OptionsPickerView optionsPickerView = new OptionsPickerView(activity);
        optionsPickerView.setTitle(title);
        optionsPickerView.setPicker(options1Items, options2Items, options3Items, linkage);
        optionsPickerView.setSelectOptions(1, 1, 1);
        optionsPickerView.setOnoptionsSelectListener(listener);
        optionsPickerView.show();
        return optionsPickerView;
    }
}
