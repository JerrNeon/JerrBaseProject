package com.cw.andoridmvp.fragment;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.fragment.BaseFragment;
import com.cw.andoridmvp.bean.PieData;
import com.cw.andoridmvp.db.MyContentProvider;
import com.cw.andoridmvp.util.form.FormUtils;
import com.cw.andoridmvp.util.form.RegexConstans;
import com.cw.andoridmvp.widget.PieView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (测试表单验证)
 * @create by: chenwei
 * @date 2016/8/23 15:29
 */
public class TabInfoFragment extends BaseFragment {

    @BindView(R.id.tv1)
    FormEditText tv1;
    @BindView(R.id.tv2)
    FormEditText tv2;
    @BindView(R.id.tv3)
    FormEditText tv3;
    @BindView(R.id.tv4)
    FormEditText tv4;
    @BindView(R.id.textinputlayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.pieview)
    PieView mPieView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        //mTextInputLayout.getEditText().addTextChangedListener(new MyTextWatcher(mTextInputLayout, "用户名长度不能小于6位"));
        //Log.i("tag", getParemters(getClass()));
        initPieView();
        initDb();
        initReceiver();
        return mView;
    }

    private void initPieView() {
        List<PieData> datas = new ArrayList<>();
        datas.add(new PieData(30));
        datas.add(new PieData(70));
        datas.add(new PieData(140));
        datas.add(new PieData(300));
        datas.add(new PieData(200));
        mPieView.setStrage(270);
        mPieView.setDatas(datas);
    }


    private void initDb() {
        ArrayList<ContentProviderOperation> list = new ArrayList<>();
        ContentProviderOperation op1 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "刘宓")
                .build();
        ContentProviderOperation op2 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "岑胜")
                .build();
        ContentProviderOperation op3 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "张明")
                .build();
        ContentProviderOperation op4 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "吴越")
                .build();
        ContentProviderOperation op5 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "陈胜")
                .build();
        ContentProviderOperation op6 = ContentProviderOperation.newInsert(Uri.parse(MyContentProvider.content_persons))
                .withValue("name", "周全")
                .build();
        list.add(op1);
        list.add(op2);
        list.add(op3);
        list.add(op4);
        list.add(op5);
        list.add(op6);
//        try {
              //添加
//            getContext().getContentResolver().applyBatch(MyContentProvider.autors, list);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (OperationApplicationException e) {
//            e.printStackTrace();
//        }
        ContentValues value = new ContentValues();
        value.put("name", "柯南");
        //修改
        getContext().getContentResolver().update(Uri.parse(MyContentProvider.content_person), value, "_id=?", new String[]{"2"});
        //删除
        getContext().getContentResolver().delete(Uri.parse(MyContentProvider.content_person),"_id=?",new String[]{"1"});
        //查询
        Cursor cursor = getContext().getContentResolver().query(Uri.parse(MyContentProvider.content_persons), null, null, null, null);
        Log.i("tag", "getCount--->" + cursor.getCount());//多少条数据
        Log.i("tag", "getColumnCount--->" + cursor.getColumnCount());//表中有多少列
        while (cursor.moveToNext()) {
            Log.i("tag", "_id--->" + cursor.getInt(cursor.getColumnIndex("_id")));
            Log.i("tag", "name--->" + cursor.getString(cursor.getColumnIndex("name")));
        }
    }

    private void initReceiver(){
        Intent intent = new Intent("com.cw.mvp.myreceiver");
        intent.putExtra("argument","已接受到新的信息");
        //mContext.sendBroadcast(intent);//普通广播
        mContext.sendOrderedBroadcast(intent,null);//有序广播
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_tabinfo;
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (FormUtils.validate(mContext, new String[]{"用户名", "密码", "邮箱", "手机号"}, tv1, tv2, tv3, tv4))
            return;
        if (FormUtils.validateAll(mContext, new String[]{RegexConstans.regexPassWord}, new String[]{"密码只能为8到16位数字字母下划线的组合"}, tv2))
            return;
        if (FormUtils.validate(mContext, new String[]{RegexConstans.regexEmail}, new String[]{"邮箱"}, tv3))
            return;
        if (FormUtils.validate(tv1, tv2, tv3, tv4)) {
            Toast.makeText(mContext, "验证正确", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(mContext, "验证失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onButton() {
        Snackbar.make(mView, "带按钮的snackBar", Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mView, "snackBar", Snackbar.LENGTH_LONG).show();
            }
        }).show();
    }

    class MyTextWatcher implements TextWatcher {
        private TextInputLayout mTextInputLayout;
        private String errorInfo;

        public MyTextWatcher(TextInputLayout textInputLayout, String errorInfo) {
            this.mTextInputLayout = textInputLayout;
            this.errorInfo = errorInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mTextInputLayout.getEditText().getText().toString().length() < 6) {
                mTextInputLayout.setErrorEnabled(true);//是否设置错误提示信息
                mTextInputLayout.setError(errorInfo);//设置错误提示信息
            } else {
                mTextInputLayout.setErrorEnabled(false);//不设置错误提示信息
            }
        }
    }
}


